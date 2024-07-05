package prography.team5.server.card.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.HotVoteRepository;
import prography.team5.server.card.service.dto.CategoryHotVotePreviewsResponse;
import prography.team5.server.card.service.dto.HotVotePreviewsResponse;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;
import prography.team5.server.category.domain.HotVotesDesign;

@RequiredArgsConstructor
@Service
public class HotVoteService {

    private final HotVoteRepository hotVoteRepository;
    private final HotVotesDesign hotVotesDesign;
    private final CategoryRepository categoryRepository;

    //todo: 리팩터링할때 readOnly 인지 확인
    @Transactional(readOnly = true)
    public HotVotePreviewsResponse findHotVotes(final int size) {
        List<VoteCard> votes = hotVoteRepository.findHotVotes(size);
        return HotVotePreviewsResponse.toResponse(votes, hotVotesDesign);
    }

    @Transactional(readOnly = true)
    public CategoryHotVotePreviewsResponse findHotVotesByCategoryId(final Integer size, final Long categoryId) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);
        final Category category = categoryRepository.findById(categoryId).orElseThrow();
        final List<VoteCard> votes = hotVoteRepository.findHotVotesOfCategory(size, categoryId, startDate, endDate);
        return CategoryHotVotePreviewsResponse.toHotVoteResponse(category, startDate, endDate, votes);
    }
}
