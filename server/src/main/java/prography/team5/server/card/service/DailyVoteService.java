package prography.team5.server.card.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import prography.team5.server.card.domain.DailyVoteCard;
import prography.team5.server.card.repository.DailyVoteCardRepository;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.VoteCardRepository;
import prography.team5.server.card.service.dto.DailyVoteResponse;

@RequiredArgsConstructor
@Service
public class DailyVoteService {

    private final DailyVoteCardRepository dailyVoteCardRepository;
    private final VoteCardRepository voteCardRepository;

    //todo: 오늘의 투표 선정 정책은? 일단은 가장 투표수 낮은거 하나 내보냄.
    public DailyVoteResponse getTodayVote() {
        final LocalDate today = LocalDate.now();
        final Optional<DailyVoteCard> dailyVoteCard = dailyVoteCardRepository.findByDate(today);
        if(dailyVoteCard.isPresent()) {
            return DailyVoteResponse.toResponse(dailyVoteCard.get().getVoteCard());
        }
        final List<VoteCard> withFewestParticipants = voteCardRepository.findWithFewestParticipants(
                PageRequest.ofSize(1)
        );
        final VoteCard voteCard = withFewestParticipants.get(0);
        dailyVoteCardRepository.save(new DailyVoteCard(voteCard));
        return DailyVoteResponse.toResponse(voteCard);
    }
}
