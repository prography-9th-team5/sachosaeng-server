package prography.team5.server.card.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prography.team5.server.card.domain.UserVoteOption;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.UserVoteOptionRepository;
import prography.team5.server.card.repository.VoteCardRepository;
import prography.team5.server.user.domain.User;
import prography.team5.server.user.domain.UserRepository;
import prography.team5.server.user.domain.UserType;

@RequiredArgsConstructor
@Component
public class UserVotingAnalysis {

    private final UserVoteOptionRepository userVoteOptionRepository;
    private final UserRepository userRepository;
    private final VoteCardRepository voteCardRepository;
    private static final Map<UserType, String> votedFormat = new EnumMap<>(UserType.class);
    private static final Map<UserType, String> notVotedFormat = new EnumMap<>(UserType.class);

    static {
        votedFormat.put(UserType.NEW_EMPLOYEE, "나와 같은 1~3년차 직장인은\n%s에 가장 많이 투표했어요!");
        votedFormat.put(UserType.STUDENT, "나와 같은 학생은\n%s에 가장 많이 투표했어요!");
        votedFormat.put(UserType.JOB_SEEKER, "나와 같은 취준생은\n%s에 가장 많이 투표했어요!");
        votedFormat.put(UserType.OTHER, "나와 같은 유저는\n%s에 가장 많이 투표했어요!");

        notVotedFormat.put(UserType.NEW_EMPLOYEE, "나와 같은 1~3년차 직장인은\n아직 투표하지 않았어요!");
        notVotedFormat.put(UserType.STUDENT, "나와 같은 학생은\n아직 투표하지 않았어요!");
        notVotedFormat.put(UserType.JOB_SEEKER, "나와 같은 취준생은\n아직 투표하지 않았어요!");
        notVotedFormat.put(UserType.OTHER, "나와 같은 유저는\n아직 투표하지 않았어요!");
    }

    public String analyzeResult(final long voteId, final Long userId) {
        final User user = userRepository.findById(userId).orElseThrow();
        final UserType userType = user.getUserType();

        List<UserVoteOption> voteOptions = userVoteOptionRepository.findByVoteIdAndUserType(voteId, userType);
        voteOptions.removeIf(option -> option.getUserId().equals(userId));
        if (voteOptions.isEmpty()) {
            return notVotedFormat.get(userType);
        }

        // VoteOptions를 voteOptionId에 따라 분류하여 개수를 센다.
        Map<Long, Long> voteCount = voteOptions.stream()
                .collect(Collectors.groupingBy(UserVoteOption::getVoteOptionId, Collectors.counting()));

        // 개수가 가장 많은 voteOptionId를 선택
        Long mostVotedOptionId = voteCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();

        // voteRepository에서 voteOptionId로 선택지 이름을 가져온다.
        final VoteCard voteCard = voteCardRepository.findById(voteId).orElseThrow();
        final String voteOption = voteCard.findVoteOption(mostVotedOptionId);

        // 포맷에 따라 문장을 만들어 반환한다.
        return String.format(votedFormat.get(userType), voteOption);
    }

    public Map<Long, Boolean> analyzeIsVoted(final List<Long> voteIds, final Long userId) {
        //userId가 null이라면 모든 voteId에 대해 false를 저장
        if (userId == null) {
            return voteIds.stream()
                    .collect(Collectors.toMap(voteId -> voteId, voteId -> false));
        }

        List<UserVoteOption> userVoteOptions = userVoteOptionRepository.findByUserIdAndVoteIdIn(userId, voteIds);
        Set<Long> voteIdsThatUserAlreadyVoted = userVoteOptions.stream()
                .map(UserVoteOption::getVoteId)
                .collect(Collectors.toSet());

        return voteIds.stream()
                .collect(Collectors.toMap(voteId -> voteId, voteIdsThatUserAlreadyVoted::contains));
    }
}
