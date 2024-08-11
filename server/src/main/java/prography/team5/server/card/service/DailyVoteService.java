package prography.team5.server.card.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.card.domain.DailyVoteCard;
import prography.team5.server.card.repository.DailyVoteCardRepository;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.UserVoteOptionRepository;
import prography.team5.server.card.repository.VoteCardRepository;
import prography.team5.server.card.service.dto.SimpleVoteWithCategoryResponse;

@RequiredArgsConstructor
@Service
public class DailyVoteService {

    private final DailyVoteCardRepository dailyVoteCardRepository;
    private final VoteCardRepository voteCardRepository;
    private final UserVotingAnalysis userVotingAnalysis;

    @Transactional
    public SimpleVoteWithCategoryResponse getTodayVote(final Accessor accessor) {
        final LocalDate today = LocalDate.now();
        final Optional<DailyVoteCard> dailyVoteCard = dailyVoteCardRepository.findByDate(today);
        if(dailyVoteCard.isPresent()) {
            final VoteCard voteCard = dailyVoteCard.get().getVoteCard();
            final boolean isVoted = userVotingAnalysis.analyzeIsVoted(voteCard.getId(), accessor.id());
            return SimpleVoteWithCategoryResponse.toResponseWith18px(voteCard, isVoted);
        }
        Integer minParticipantCount = voteCardRepository.findMinParticipantCount();
        final List<VoteCard> withFewestParticipants = voteCardRepository.findWithFewestParticipants(minParticipantCount);
        Collections.shuffle(withFewestParticipants);

        if (withFewestParticipants.isEmpty()) {
            throw new IllegalArgumentException("No votes");
        }
        final VoteCard voteCard = withFewestParticipants.get(0);
        dailyVoteCardRepository.save(new DailyVoteCard(voteCard));
        final boolean isVoted = userVotingAnalysis.analyzeIsVoted(voteCard.getId(), accessor.id());
        return SimpleVoteWithCategoryResponse.toResponseWith18px(voteCard, isVoted);
    }
}
