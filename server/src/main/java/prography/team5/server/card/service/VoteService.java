package prography.team5.server.card.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.card.domain.SortType;
import prography.team5.server.card.domain.UserVoteOption;
import prography.team5.server.card.domain.UserVoteOptionRepository;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.domain.VoteCardRepository;
import prography.team5.server.card.service.dto.SimpleVoteResponse;
import prography.team5.server.card.service.dto.VoteOptionChoiceRequest;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;
import prography.team5.server.mycategory.domain.MyCategory;
import prography.team5.server.mycategory.domain.MyCategoryRepository;
import prography.team5.server.card.service.dto.CategoryVoteSuggestionsResponse;
import prography.team5.server.card.service.dto.HotVotePreviewsResponse;
import prography.team5.server.card.service.dto.VoteIdResponse;
import prography.team5.server.card.service.dto.VoteRequest;
import prography.team5.server.card.service.dto.VoteResponse;
import prography.team5.server.user.domain.User;
import prography.team5.server.user.domain.UserRepository;
import prography.team5.server.user.domain.UserType;

@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteCardRepository voteCardRepository;
    private final CategoryRepository categoryRepository;
    private final MyCategoryRepository myCategoryRepository;
    private final UserRepository userRepository;
    private final UserVoteOptionRepository userVoteOptionRepository;

    @Transactional(readOnly = true)
    public VoteResponse findByVoteId(final Long userId, final long voteId, final Long categoryId) {
        final VoteCard voteCard = voteCardRepository.findById(voteId)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_VOTE_CARD_ID));
        Category category;
        if(categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_CATEGORY));
            voteCard.checkCategory(category);
        } else {
            category = voteCard.getCategories().get(0);
        }
        final List<UserVoteOption> voted = userVoteOptionRepository.findByUserIdAndVoteId(userId, voteId);
        if(!voted.isEmpty()) {
            final List<Long> voteOptionIds = voted.stream().map(UserVoteOption::getVoteOptionId).toList();
            return VoteResponse.toResponse(category, true, voteOptionIds, voteCard);
        }
        return VoteResponse.toResponse(category, false, Collections.emptyList(), voteCard);
    }

    @Transactional(readOnly = true)
    public List<SimpleVoteResponse> findAll(
            final Long cursor,
            final Long categoryId,
            final Integer pageSize,
            final SortType sortType
    ) {
        //todo: 인기순/최신순 조회
        final PageRequest pageRequest = PageRequest.ofSize(pageSize);
        if (Objects.isNull(categoryId)) {
            return SimpleVoteResponse.toResponse(findAll(cursor, pageRequest));
        }
        return SimpleVoteResponse.toResponse(findAllByCategoryId(cursor, categoryId, pageRequest));
    }

    private List<VoteCard> findAll(final Long cursor, final PageRequest pageRequest) {
        if (Objects.isNull(cursor)) {
            return voteCardRepository.findLatestCards(pageRequest).getContent();
        }
        return voteCardRepository.findBeforeCursor(cursor, pageRequest).getContent();
    }

    private List<VoteCard> findAllByCategoryId(final Long cursor, final long categoryId,
                                                   final PageRequest pageRequest) {
        if (Objects.isNull(cursor)) {
            return voteCardRepository.findLatestCardsByCategoriesId(categoryId, pageRequest).getContent();
        }
        return voteCardRepository.findByCategoriesIdBeforeCursor(cursor, categoryId, pageRequest).getContent();
    }

    @Transactional(readOnly = true)
    public List<CategoryVoteSuggestionsResponse> findSuggestions(final Long userId) {
        final List<MyCategory> myCategories = myCategoryRepository.findAllByUserId(userId);
        //todo: 지금은 최신순으로 임시 땜빵중 로테이션 돌리는 로직으로 변경요망
        if(myCategories.isEmpty()) {
            final User user = userRepository.findById(userId).orElseThrow();
            return suggestBasedOnUserType(user.getUserType());
        }
        return suggestBasedOnMyCategories(myCategories);
    }

    private List<CategoryVoteSuggestionsResponse> suggestBasedOnUserType(final UserType userType) {
        final List<Category> categories = categoryRepository.findAllByUserType(userType);
        List<CategoryVoteSuggestionsResponse> response = new ArrayList<>();
        for (Category category : categories) {
            final List<VoteCard> votes = voteCardRepository.findLatestCardsByCategoriesId(
                    category.getId(),
                    PageRequest.ofSize(3)
            ).getContent();
            response.add(CategoryVoteSuggestionsResponse.toResponse(category, votes));
        }
        return response;
    }

    private List<CategoryVoteSuggestionsResponse> suggestBasedOnMyCategories(
            final List<MyCategory> myCategories) {
        List<CategoryVoteSuggestionsResponse> response = new ArrayList<>();
        for (MyCategory myCategory : myCategories) {
            final Category category = myCategory.getCategory();
            final List<VoteCard> votes = voteCardRepository.findLatestCardsByCategoriesId(
                    category.getId(),
                    PageRequest.ofSize(3)
            ).getContent();
            response.add(CategoryVoteSuggestionsResponse.toResponse(category, votes));
        }
        return response;
    }

    @Transactional
    public void chooseVoteOption(final Long userId, final long voteId, final VoteOptionChoiceRequest request) {
        final VoteCard voteCard = voteCardRepository.findById(voteId)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_VOTE_CARD_ID));
        final List<UserVoteOption> votedBefore = userVoteOptionRepository.findByUserIdAndVoteId(userId, voteId);
        final List<Long> voteOptionIdsNew = request.chosenVoteOptionIds();
        final List<UserVoteOption> votedNow = voteOptionIdsNew.stream()
                .map(each -> new UserVoteOption(userId, voteId, each)).toList();
        if(votedBefore.isEmpty()) {
            voteCard.chooseVoteOption(voteOptionIdsNew);
            userVoteOptionRepository.saveAll(votedNow);
            return;
        }
        final List<Long> voteOptionIdesBefore = votedBefore.stream().map(UserVoteOption::getVoteOptionId).toList();
        voteCard.changeVoteOption(voteOptionIdesBefore, voteOptionIdsNew);
        userVoteOptionRepository.saveAll(votedNow);
        userVoteOptionRepository.deleteAll(votedBefore);
    }
}
