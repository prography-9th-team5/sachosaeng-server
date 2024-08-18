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

    @Scheduled(cron = "0 25 2 * * *", zone = "Asia/Seoul")
    @Transactional
    public void scheduleTomorrowSuggestionVote() {
        // 다음날 날짜를 계산합니다.
        final LocalDate tomorrow = LocalDate.now().plusDays(1);
        final LocalDate sixDaysAgo = LocalDate.now().minusDays(6);

        log.info("suggestion-vote empty! At " + LocalDateTime.now() + ", start creating suggestion votes for " + tomorrow);
        final List<SuggestionVoteCard> recentSuggestionVotes = suggestionVoteCardRepository.findRecentSuggestionVoteCards(sixDaysAgo);
        final List<Category> categories = categoryRepository.findAll();
        final List<VoteCard> votes = voteCardRepository.findAll();
        List<SuggestionVoteCard> save = new ArrayList<>();
        for (Category category : categories) {
            final List<VoteCard> recent = recentSuggestionVotes.stream()
                    .map(SuggestionVoteCard::getVoteCard).filter(voteCard -> voteCard.isSameCategory(category)).toList();
            final List<VoteCard> cardsOfCategory = new ArrayList<>(votes.stream().filter(each -> each.isSameCategory(category)).toList());
            final List<VoteCard> notRecent = new ArrayList<>(
                    cardsOfCategory.stream().filter(each -> !recent.contains(each)).toList());
            Collections.shuffle(notRecent);

            // 3개를 선택
            int length = Math.min(notRecent.size(), 3);
            List<VoteCard> selectedCards = new ArrayList<>(notRecent.subList(0, length));

            if (length < 3 && cardsOfCategory.size() > length) {
                // notRecent에 부족한 부분을 cardsOfCategory에서 채움 (중복 제외)
                List<VoteCard> remainingCards = new ArrayList<>(cardsOfCategory);
                remainingCards.removeAll(selectedCards);
                if (!remainingCards.isEmpty()) {
                    Collections.shuffle(remainingCards);
                    final int toAdd = Math.min(remainingCards.size(), 3 - length);
                    selectedCards.addAll(remainingCards.subList(0, toAdd));
                }
            }

            for (VoteCard selectedCard : selectedCards) {
                save.add(new SuggestionVoteCard(
                        selectedCard,
                        category,
                        tomorrow
                ));
            }
        }
        suggestionVoteCardRepository.saveAll(save);
        log.info("success creating suggestion-votes!");
    }
}
