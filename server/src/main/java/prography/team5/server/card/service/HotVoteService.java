package prography.team5.server.card.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.card.repository.HotVoteRepository;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.service.dto.HotVotePreviewsResponse;

@RequiredArgsConstructor
@Service
public class HotVoteService {

    private final HotVoteRepository hotVoteRepository;

    @Transactional(readOnly = true)
    public HotVotePreviewsResponse findHotVotes(final Long categoryId, final int size) {
        if(Objects.isNull(categoryId)) {
            List<VoteCard> votes = hotVoteRepository.findHotVotes(size);
            return HotVotePreviewsResponse.toResponse(votes);
        }
        List<VoteCard> votes = hotVoteRepository.findHotVotesOfCategory(3, categoryId);
        return HotVotePreviewsResponse.toResponse(votes);
    }
}
