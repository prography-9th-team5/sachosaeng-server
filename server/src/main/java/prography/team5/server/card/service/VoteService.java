package prography.team5.server.card.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.bookmark.domain.VoteCardBookmarkRepository;
import prography.team5.server.card.domain.Card;
import prography.team5.server.card.domain.SortType;
import prography.team5.server.card.domain.SuggestionVoteCard;
import prography.team5.server.card.domain.UserVoteOption;
import prography.team5.server.card.repository.SuggestionVoteCardRepository;
import prography.team5.server.card.repository.UserVoteOptionRepository;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.VoteCardRepository;
import prography.team5.server.card.service.dto.CategoryVotePreviewsResponse;
import prography.team5.server.card.service.dto.SimpleVoteResponse;
import prography.team5.server.card.service.dto.VoteOptionChoiceRequest;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;
import prography.team5.server.mycategory.domain.MyCategory;
import prography.team5.server.mycategory.domain.MyCategoryRepository;
import prography.team5.server.card.service.dto.CategoryVoteSuggestionsResponse;
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
    private final UserVotingAnalysis userVotingAnalysis;
    private final SuggestionVoteCardRepository suggestionVoteCardRepository;
    private final VoteCardBookmarkRepository voteCardBookmarkRepository;

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
        String analysis = userVotingAnalysis.analyzeResult(voteId, userId);
        final boolean isBookmarked = voteCardBookmarkRepository.existsByVoteCardAndUserId(voteCard, userId);
        if(!voted.isEmpty()) { // 유저가 투표한 경우
            final List<Long> voteOptionIds = voted.stream().map(UserVoteOption::getVoteOptionId).toList();
            return VoteResponse.toResponseWith32px(category, true, isBookmarked, voteOptionIds, voteCard, analysis);
        }
        return VoteResponse.toResponseWith32px(category, false, isBookmarked, Collections.emptyList(), voteCard, analysis);
    }

    //todo: 리팩터링 완전 필요 -> 관리자 페이지에서 사용함
    @Transactional(readOnly = true)
    public List<SimpleVoteResponse> findAll(
            final Long cursor,
            final Long categoryId,
            final Integer pageSize,
            final SortType sortType
    ) {
        final PageRequest pageRequest = PageRequest.ofSize(pageSize);
        Map<Long, Boolean> tmp = new HashMap<>();
        if (Objects.isNull(categoryId)) {
            return SimpleVoteResponse.toResponse(findAll(cursor, pageRequest), tmp);
        }
        if (Objects.isNull(cursor)) {
            return SimpleVoteResponse.toResponse(voteCardRepository.findLatestCardsByCategoriesId(categoryId, pageRequest).getContent(), tmp);
        }
        return SimpleVoteResponse.toResponse(voteCardRepository.findByCategoriesIdBeforeCursor(cursor, categoryId, pageRequest).getContent(), tmp);
    }

    private List<VoteCard> findAll(final Long cursor, final PageRequest pageRequest) {
        if (Objects.isNull(cursor)) {
            return voteCardRepository.findLatestCards(pageRequest).getContent();
        }
        return voteCardRepository.findBeforeCursor(cursor, pageRequest).getContent();
    }

    //todo: accessor를 어떻게 잘 활용해볼수 없을까?
    @Transactional(readOnly = true)
    public CategoryVotePreviewsResponse findAllByCategoryId(
            final Accessor accessor,
            final Long cursor,
            final long categoryId,
            final Integer size
    ) {
        final PageRequest pageRequest = PageRequest.ofSize(size);
        if (Objects.isNull(cursor)) {
            final Slice<VoteCard> slice = voteCardRepository.findLatestCardsByCategoriesId(categoryId, pageRequest);
            final List<VoteCard> votes = slice.getContent();
            Long nextCursor = null;
            if(!votes.isEmpty()) {
                nextCursor = votes.getLast().getId();
            }
            final Map<Long, Boolean> isVotedAnalysis = userVotingAnalysis.analyzeIsVoted(
                    votes.stream().map(Card::getId).toList(),
                    accessor.id()
            );
            return CategoryVotePreviewsResponse.toResponse(votes, isVotedAnalysis, slice.hasNext(), nextCursor);
        }
        final Slice<VoteCard> slice = voteCardRepository.findByCategoriesIdBeforeCursor(cursor, categoryId, pageRequest);
        final List<VoteCard> votes = slice.getContent();
        Long nextCursor = null;
        if(!votes.isEmpty()) {
            nextCursor = votes.getLast().getId();
        }
        final Map<Long, Boolean> isVotedAnalysis = userVotingAnalysis.analyzeIsVoted(
                votes.stream().map(Card::getId).toList(),
                accessor.id()
        );
        return CategoryVotePreviewsResponse.toResponse(votes, isVotedAnalysis, slice.hasNext(), nextCursor);
    }

    @Transactional(readOnly = true)
    public List<CategoryVoteSuggestionsResponse> findSuggestionsOfAllCategories(final Accessor accessor) {
        final List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "priority"));
        final List<SuggestionVoteCard> suggestionCards = suggestionVoteCardRepository.findAllByViewDate(LocalDate.now());
        List<CategoryVoteSuggestionsResponse> response = new ArrayList<>();
        for (Category category : categories) {
            final List<VoteCard> votes = suggestionCards.stream().filter(each -> each.getCategory().equals(category))
                    .map(SuggestionVoteCard::getVoteCard).toList();
            final Map<Long, Boolean> isVotedAnalysis = userVotingAnalysis.analyzeIsVoted(
                    votes.stream().map(Card::getId).toList(),
                    accessor.id()
            );
            response.add(CategoryVoteSuggestionsResponse.toResponse(category, votes, isVotedAnalysis));
        }
        return response;
    }

    @Transactional(readOnly = true)
    public List<CategoryVoteSuggestionsResponse> findSuggestionsOfMy(final Long userId) {
        final List<MyCategory> myCategories = myCategoryRepository.findAllByUserIdOrderByCategoryPriorityAsc(userId);
        if(myCategories.isEmpty()) {
            final User user = userRepository.findById(userId).orElseThrow();
            return suggestBasedOnUserType(user.getUserType(), userId);
        }
        return suggestBasedOnMyCategories(myCategories, userId);
    }

    private List<CategoryVoteSuggestionsResponse> suggestBasedOnUserType(final UserType userType, final long userId) {
        final List<Category> categories = categoryRepository.findAllByUserType(userType);
        final List<SuggestionVoteCard> suggestionCards = suggestionVoteCardRepository.findAllByViewDate(LocalDate.now());
        List<CategoryVoteSuggestionsResponse> response = new ArrayList<>();
        for (Category category : categories) {
            final List<VoteCard> votes = suggestionCards.stream().filter(each -> each.getCategory().equals(category))
                    .map(SuggestionVoteCard::getVoteCard).toList();
            final Map<Long, Boolean> isVotedAnalysis = userVotingAnalysis.analyzeIsVoted(
                    votes.stream().map(Card::getId).toList(),
                    userId
            );
            response.add(CategoryVoteSuggestionsResponse.toResponse(category, votes, isVotedAnalysis));
        }
        return response;
    }

    private List<CategoryVoteSuggestionsResponse> suggestBasedOnMyCategories(
            final List<MyCategory> myCategories,
            final long userId
    ) {
        List<CategoryVoteSuggestionsResponse> response = new ArrayList<>();
        final List<SuggestionVoteCard> suggestionCards = suggestionVoteCardRepository.findAllByViewDate(LocalDate.now());
        for (MyCategory myCategory : myCategories) {
            final Category category = myCategory.getCategory();
            final List<VoteCard> votes = suggestionCards.stream().filter(each -> each.getCategory().equals(category))
                    .map(SuggestionVoteCard::getVoteCard).toList();
            final Map<Long, Boolean> isVotedAnalysis = userVotingAnalysis.analyzeIsVoted(
                    votes.stream().map(Card::getId).toList(),
                    userId
            );
            response.add(CategoryVoteSuggestionsResponse.toResponse(category, votes, isVotedAnalysis));
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
