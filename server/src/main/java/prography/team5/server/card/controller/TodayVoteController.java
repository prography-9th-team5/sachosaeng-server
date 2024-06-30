package prography.team5.server.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.card.service.DailyVoteService;
import prography.team5.server.card.service.dto.DailyVoteResponse;
import prography.team5.server.common.CommonApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/votes/daily")
public class TodayVoteController {

    private final DailyVoteService dailyVoteService;

    @GetMapping
    public ResponseEntity<CommonApiResponse<DailyVoteResponse>> findTodayVote() {
        DailyVoteResponse response = dailyVoteService.getTodayVote();
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}
