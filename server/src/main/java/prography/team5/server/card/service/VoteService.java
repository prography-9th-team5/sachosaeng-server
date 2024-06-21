package prography.team5.server.card.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.card.domain.SortType;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.domain.VoteCardRepository;
import prography.team5.server.card.service.dto.SimpleVoteResponse;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;
import prography.team5.server.mycategory.domain.MyCategory;
import prography.team5.server.mycategory.domain.MyCategoryRepository;
import prography.team5.server.card.service.dto.CategoryVoteSuggestionsResponse;
import prography.team5.server.card.service.dto.HotVotePreviewsResponse;
import prography.team5.server.card.service.dto.VoteIdResponse;
import prography.team5.server.card.service.dto.VoteRequest;
import prography.team5.server.card.service.dto.VoteResponse;

@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteCardRepository voteCardRepository;
    private final CategoryRepository categoryRepository;
    private final MyCategoryRepository myCategoryRepository;

    @Transactional
    public VoteIdResponse create(final VoteRequest voteRequest, final Long userId) {
        //todo: 존재하지 않는 카테고리 처리
        final List<Category> categories = categoryRepository.findAllByIdIn(voteRequest.categoryIds());
        final VoteCard voteCard = new VoteCard(voteRequest.title(), categories, userId);
        voteRequest.voteOptions()
                .forEach(voteCard::addVoteOption);
        return new VoteIdResponse(voteCardRepository.save(voteCard).getId());
    }

    @Transactional(readOnly = true)
    public VoteResponse findByVoteId(final long voteId) {
        final VoteCard voteCard = voteCardRepository.findById(voteId).orElseThrow(); //todo: 예외처리
        return VoteResponse.toResponse(voteCard);
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

    @Transactional(readOnly = true)
    public List<VoteResponse> findAllContainContents(final Long cursor, final Long categoryId, final Integer pageSize) {
        final PageRequest pageRequest = PageRequest.ofSize(pageSize);
        if (Objects.isNull(categoryId)) {
            return VoteResponse.toResponse(findAll(cursor, pageRequest));
        }
        return VoteResponse.toResponse(findAllByCategoryId(cursor, categoryId, pageRequest));
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
    public HotVotePreviewsResponse findHotVotes() {
        //todo: 지금 임시 땜빵중이라 인기투표 선정 로직으로 변경 요망
        final List<VoteCard> votes = voteCardRepository.findLatestCards(
                PageRequest.ofSize(3)
        ).getContent();
        return HotVotePreviewsResponse.toResponse(votes);
    }

    @Transactional(readOnly = true)
    public List<CategoryVoteSuggestionsResponse> findSuggestions(final Long userId) {
        final List<MyCategory> myCategories = myCategoryRepository.findAllByUserId(userId);
        List<CategoryVoteSuggestionsResponse> response = new ArrayList<>();
        //todo: 지금은 임시 땜빵중 로테이션 돌리는 로직으로 변경요망
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
}
