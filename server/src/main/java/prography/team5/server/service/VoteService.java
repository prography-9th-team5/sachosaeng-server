package prography.team5.server.service;

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
import prography.team5.server.service.dto.VoteIdResponse;
import prography.team5.server.service.dto.VoteRequest;
import prography.team5.server.service.dto.VoteResponse;

@RequiredArgsConstructor
@Service
public class VoteService {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final VoteCardRepository voteCardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public VoteIdResponse create(final VoteRequest voteRequest, final long userId) {
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
}
