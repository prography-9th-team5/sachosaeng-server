package prography.team5.server.card.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.card.domain.HotVoteRepository;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.domain.VoteCardRepository;
import prography.team5.server.card.service.dto.HotVotePreviewsResponse;

@RequiredArgsConstructor
@Service
public class HotVoteService {

    private final VoteCardRepository voteCardRepository;
    private final HotVoteRepository hotVoteRepository;

    @Transactional(readOnly = true)
    public HotVotePreviewsResponse findHotVotes(final Long categoryId, final int size) {
        //todo: 지금 임시 땜빵중이라 인기투표 선정 로직으로 변경 요망
        if(Objects.isNull(categoryId)) {
            List<VoteCard> votes = hotVoteRepository.findHotVotesOfAllCategory(size);
            return HotVotePreviewsResponse.toResponse(votes);
        }
        List<VoteCard> votes = hotVoteRepository.findHotVotesOfCategory(3, categoryId);
        return HotVotePreviewsResponse.toResponse(votes);
    }
}
