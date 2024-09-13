package prography.team5.server.bookmark.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prography.team5.server.card.domain.UserVoteOption;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.UserVoteOptionRepository;

@RequiredArgsConstructor
@Component
public class VoteCardBookmarkDescription {

    private final UserVoteOptionRepository userVoteOptionRepository;

    public Map<Long, String> createDescriptions(final List<VoteCard> voteCards) {
        Map<Long, String> descriptions = new HashMap<>();

        if (voteCards.isEmpty()) {
            return descriptions;
        }

        final List<Long> voteCardIds = voteCards.stream()
                .map(each -> each.getId())
                .toList();
        List<UserVoteOption> userVoteOptions = userVoteOptionRepository.findByVoteIdIn(voteCardIds);

        // voteCard별로 묶기
        Map<Long, List<UserVoteOption>> optionsByVoteCardId = userVoteOptions.stream()
                .collect(Collectors.groupingBy(UserVoteOption::getVoteId));

        // 각 VoteCard에 대해 가장 득표수가 많은 옵션을 선택하고 득표율을 계산
        for (VoteCard voteCard : voteCards) {
            List<UserVoteOption> options = optionsByVoteCardId.get(voteCard.getId());

            if (options.isEmpty()) {
                descriptions.put(voteCard.getId(), "");
            }
            // 옵션별로 묶기
            Map<Long, Long> optionsCount = options.stream()
                    .collect(Collectors.groupingBy(
                            UserVoteOption::getVoteOptionId,  // 그룹화 기준: VoteOptionId
                            Collectors.counting()            // 각 그룹에 대해 개수 세기
                    ));

            // 가장 많은 득표수를 가진 옵션 찾기
            Map.Entry<Long, Long> topOption = optionsCount.entrySet().stream()
                    .max(Map.Entry.comparingByValue())  // 득표수가 가장 많은 옵션 선택
                    .orElse(null);

            if (topOption == null) {
                descriptions.put(voteCard.getId(), "");
                continue;
            }

            // 전체 득표수 계산
            long totalVotes = optionsCount.values().stream().mapToLong(Long::longValue).sum();

            // 득표율 계산 (소수점 아래 1자리까지)
            double votePercentage = (double) topOption.getValue() / totalVotes * 100;
            String formattedPercentage = String.format("%.1f", votePercentage);

            // 결과를 descriptions 맵에 추가 (옵션 ID와 득표율)
            String option = voteCard.getVoteOptionById(topOption.getKey());
            descriptions.put(voteCard.getId(), formattedPercentage + "% " + option);
        }

        return descriptions;
    }
}
