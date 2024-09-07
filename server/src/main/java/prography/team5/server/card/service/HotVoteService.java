package prography.team5.server.card.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.card.domain.Card;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.HotVoteRepository;
import prography.team5.server.card.service.dto.CategoryHotVotePreviewsResponse;
import prography.team5.server.card.service.dto.HotVotePreviewsResponse;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;
import prography.team5.server.category.domain.HotVotesDesign;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;

@RequiredArgsConstructor
@Service
public class HotVoteService {

    private final HotVoteRepository hotVoteRepository;
    private final HotVotesDesign hotVotesDesign;
    private final CategoryRepository categoryRepository;
    private final UserVotingAnalysis userVotingAnalysis;

    //todo: 리팩터링할때 readOnly 인지 확인
    @Transactional(readOnly = true)
    public HotVotePreviewsResponse findHotVotes(final int size, final Accessor accessor) {
        List<VoteCard> votes = hotVoteRepository.findHotVotes(size);
        final Map<Long, Boolean> isVotedAnalysis = userVotingAnalysis.analyzeIsVoted(
                votes.stream().map(Card::getId).toList(),
                accessor.id()
        );
        return HotVotePreviewsResponse.toResponse(votes, hotVotesDesign, isVotedAnalysis);
    }

    @Transactional(readOnly = true)
    public CategoryHotVotePreviewsResponse findHotVotesByCategoryId(final Integer size, final Long categoryId, final Accessor accessor) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);
        final Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new SachosaengException(
                ErrorType.INVALID_CATEGORY));
        final List<VoteCard> votes = hotVoteRepository.findHotVotesOfCategory(size, categoryId, startDate, endDate);
        final Map<Long, Boolean> isVotedAnalysis = userVotingAnalysis.analyzeIsVoted(
                votes.stream().map(Card::getId).toList(),
                accessor.id()
        );
        return CategoryHotVotePreviewsResponse.toHotVoteResponse(category, startDate, endDate, votes, isVotedAnalysis);
    }
}
