package prography.team5.server.card.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.HotVoteRepository;
import prography.team5.server.card.service.dto.HotVotePreviewsResponse;

@RequiredArgsConstructor
@Service
public class HotVoteService {

    private final HotVoteRepository hotVoteRepository;

    @Transactional(readOnly = true)
    public HotVotePreviewsResponse findHotVotes(final int size) {
        List<VoteCard> votes = hotVoteRepository.findHotVotes(size);
        return HotVotePreviewsResponse.toResponse(votes);
    }
}
