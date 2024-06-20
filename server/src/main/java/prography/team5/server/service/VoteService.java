package prography.team5.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.card.VoteCard;
import prography.team5.server.domain.card.VoteCardRepository;
import prography.team5.server.domain.category.Category;
import prography.team5.server.domain.category.CategoryRepository;
import prography.team5.server.domain.category.MyCategory;
import prography.team5.server.domain.category.MyCategoryRepository;
import prography.team5.server.service.dto.CategoryVotePreviewsResponse;
import prography.team5.server.service.dto.HotVotePreviewsResponse;
import prography.team5.server.service.dto.VoteIdResponse;
import prography.team5.server.service.dto.VoteRequest;
import prography.team5.server.service.dto.VoteResponse;

@RequiredArgsConstructor
@Service
public class VoteService {

    private static final int DEFAULT_PAGE_SIZE = 10;

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
        return VoteResponse.from(voteCard);
    }

    @Transactional(readOnly = true)
    public List<VoteResponse> findAll(final Long cursor, final Long categoryId, final Integer pageSize) {
        final PageRequest pageRequest = createPageRequest(pageSize);
        if (Objects.isNull(categoryId)) {
            return findAll(cursor, pageRequest);
        }
        return findAllByCategoryId(cursor, categoryId, pageRequest);
    }

    private PageRequest createPageRequest(final Integer pageSize) {
        if (Objects.isNull(pageSize)) {
            return PageRequest.ofSize(DEFAULT_PAGE_SIZE);
        }
        return PageRequest.ofSize(pageSize);
    }

    private List<VoteResponse> findAll(final Long cursor, final PageRequest pageRequest) {
        if (Objects.isNull(cursor)) {
            return VoteResponse.from(voteCardRepository.findLatestCards(pageRequest).getContent());
        }
        return VoteResponse.from(voteCardRepository.findBeforeCursor(cursor, pageRequest).getContent());
    }

    private List<VoteResponse> findAllByCategoryId(final Long cursor, final long categoryId,
                                                   final PageRequest pageRequest) {
        if (Objects.isNull(cursor)) {
            return VoteResponse.from(
                    voteCardRepository.findLatestCardsByCategoriesId(categoryId, pageRequest).getContent());
        }
        return VoteResponse.from(
                voteCardRepository.findByCategoriesIdBeforeCursor(cursor, categoryId, pageRequest).getContent());
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
    public List<CategoryVotePreviewsResponse> findSuggestions(final Long userId) {
        final List<MyCategory> myCategories = myCategoryRepository.findAllByUserId(userId);
        List<CategoryVotePreviewsResponse> response = new ArrayList<>();
        //todo: 지금은 임시 땜빵중 로테이션 돌리는 로직으로 변경요망
        for (MyCategory myCategory : myCategories) {
            final Category category = myCategory.getCategory();
            final List<VoteCard> votes = voteCardRepository.findLatestCardsByCategoriesId(
                    category.getId(),
                    PageRequest.ofSize(3)
            ).getContent();
            response.add(CategoryVotePreviewsResponse.toResponse(category, votes));
        }
        return response;
    }
}
