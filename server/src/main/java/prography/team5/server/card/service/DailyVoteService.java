package prography.team5.server.card.service;

import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
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
@Slf4j
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
        log.info("daily-vote empty! At " + LocalDateTime.now() + ", start creating daily votes for today: " + today);
        Integer minParticipantCount = voteCardRepository.findMinParticipantCount();
        final List<VoteCard> withFewestParticipants = voteCardRepository.findWithFewestParticipants(minParticipantCount);
        Collections.shuffle(withFewestParticipants);

        if (withFewestParticipants.isEmpty()) {
            throw new IllegalArgumentException("No votes");
        }
        final VoteCard voteCard = withFewestParticipants.get(0);
        dailyVoteCardRepository.save(new DailyVoteCard(voteCard, LocalDate.now()));

        final boolean isVoted = userVotingAnalysis.analyzeIsVoted(voteCard.getId(), accessor.id());
        return SimpleVoteWithCategoryResponse.toResponseWith18px(voteCard, isVoted);
    }

    @Scheduled(cron = "0 0 2 * * *", zone = "Asia/Seoul")
    @Transactional
    public void scheduleTomorrowVote() {
        // 다음날 날짜를 계산합니다.
        final LocalDate tomorrow = LocalDate.now().plusDays(1);

        // 이미 내일의 DailyVoteCard가 있는지 확인합니다.
        final Optional<DailyVoteCard> dailyVoteCard = dailyVoteCardRepository.findByDate(tomorrow);
        if(dailyVoteCard.isEmpty()) {
            log.info("daily-vote empty! At " + LocalDateTime.now() + ", start creating daily votes for " + tomorrow);
            Integer minParticipantCount = voteCardRepository.findMinParticipantCount();
            final List<VoteCard> withFewestParticipants = voteCardRepository.findWithFewestParticipants(minParticipantCount);
            Collections.shuffle(withFewestParticipants);

            if (!withFewestParticipants.isEmpty()) {
                final VoteCard voteCard = withFewestParticipants.get(0);
                dailyVoteCardRepository.save(new DailyVoteCard(voteCard, tomorrow));
                log.info("daily-vote scheduling success!");
            }
        }else {
            log.info("daily-vote already exists for " + tomorrow);
        }
    }
}
