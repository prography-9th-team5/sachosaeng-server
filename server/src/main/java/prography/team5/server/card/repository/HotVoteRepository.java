package prography.team5.server.card.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.card.domain.UserVoteOption;
import prography.team5.server.card.domain.VoteCard;

@Component
@RequiredArgsConstructor
public class HotVoteRepository {

    private final VoteCardRepository voteCardRepository;
    private final UserVoteOptionRepository userVoteOptionRepository;

/*
    - 인기 투표 정의 : 최근 7일 기준 참여율 높은 투표
        *최근 7일 : 오늘 날짜를 기준으로 오늘을 포함한 일주일 전까지의 기간을 의미
        (만약 오늘이 6월 1일이라면 “최근 7일 기준”은 5월 26일부터 6월 1일까지의 기간)*/

    public List<VoteCard> findHotVotes(final int size) {
        final LocalDate today = LocalDate.now();
        final LocalDate startDate = today.minusDays(6);
        final List<UserVoteOption> history = userVoteOptionRepository.findVotesByDateRange(startDate, today);

        final Map<Long, Set<Long>> voteIdAndUserSet = history.stream()
                .collect(Collectors.groupingBy(
                        UserVoteOption::getVoteId,
                        Collectors.mapping(UserVoteOption::getUserId, Collectors.toSet())
                ));

        final Map<Long, Long> voteIdAndUniqueUserCount = voteIdAndUserSet.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (long) entry.getValue().size()
                ));

        // 가장 인기 있는 voteId 리스트를 size만큼 추출
        List<Long> topVoteIds = voteIdAndUniqueUserCount.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(size)
                .map(Map.Entry::getKey)
                .toList();

        return voteCardRepository.findAllById(topVoteIds);
    }

    // todo: 리팩터링...
    public List<VoteCard> findHotVotesOfCategory(final int size, final Long categoryId, final LocalDate startDate, final LocalDate endDate) {
        final List<UserVoteOption> history = userVoteOptionRepository.findVotesByCategoryIdAndDateRange(categoryId, startDate, endDate);
        final Map<Long, Set<Long>> voteIdAndUserSet = history.stream()
                .collect(Collectors.groupingBy(
                        UserVoteOption::getVoteId,
                        Collectors.mapping(UserVoteOption::getUserId, Collectors.toSet())
                ));

        final Map<Long, Long> voteIdAndUniqueUserCount = voteIdAndUserSet.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (long) entry.getValue().size()
                ));

        // 가장 인기 있는 voteId 리스트를 size만큼 추출
        List<Long> topVoteIds = voteIdAndUniqueUserCount.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(size)
                .map(Map.Entry::getKey)
                .toList();

        return voteCardRepository.findAllById(topVoteIds);
    }
}
