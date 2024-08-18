package prography.team5.server.card.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.card.domain.DailyVoteCard;
import prography.team5.server.card.domain.SuggestionVoteCard;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.SuggestionVoteCardRepository;
import prography.team5.server.card.repository.VoteCardRepository;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class VoteScheduler {

    private final SuggestionVoteCardRepository suggestionVoteCardRepository;
    private final VoteCardRepository voteCardRepository;
    private final CategoryRepository categoryRepository;
    @Scheduled(cron = "0 47 1 * * *", zone = "Asia/Seoul")
    @Transactional
    public void scheduleTomorrowVote() {
        // 다음날 날짜를 계산합니다.
        final LocalDate tomorrow = LocalDate.now();

        log.info("suggestion-vote empty! At " + LocalDateTime.now() + ", start creating suggestion votes for " + tomorrow);
        final List<Category> categories = categoryRepository.findAll();
        final List<VoteCard> votes = voteCardRepository.findAll();
        List<SuggestionVoteCard> save = new ArrayList<>();
        for (Category category : categories) {
            final List<VoteCard> cardsOfCategory = new ArrayList<>(votes.stream().filter(each -> each.isSameCategory(category)).toList());
            Collections.shuffle(cardsOfCategory);
            int length = Math.min(cardsOfCategory.size(), 3);
            for (int i = 0; i < length; i++) {
                save.add(new SuggestionVoteCard(
                        cardsOfCategory.get(i),
                        category,
                        tomorrow
                ));
            }
        }
        suggestionVoteCardRepository.saveAll(save);
        log.info("success creating suggestion-votes!");
    }
}
