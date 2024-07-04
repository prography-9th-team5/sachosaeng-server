package prography.team5.server.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.card.DailyVoteApiDocs;
import prography.team5.server.card.service.DailyVoteService;
import prography.team5.server.card.service.dto.SimpleVoteWithCategoryResponse;
import prography.team5.server.common.CommonApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/votes/daily")
public class DailyVoteController implements DailyVoteApiDocs {

    private final DailyVoteService dailyVoteService;

    @GetMapping
    public ResponseEntity<CommonApiResponse<SimpleVoteWithCategoryResponse>> findTodayVote() {
        SimpleVoteWithCategoryResponse response = dailyVoteService.getTodayVote();
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}
