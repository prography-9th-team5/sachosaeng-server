package prography.team5.server.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.card.HotVoteApiDocs;
import prography.team5.server.card.service.HotVoteService;
import prography.team5.server.card.service.dto.HotVotePreviewsResponse;
import prography.team5.server.common.CommonApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/votes/hot")
public class HotVoteController implements HotVoteApiDocs {

    private final HotVoteService hotVoteService;

    @GetMapping
    public ResponseEntity<CommonApiResponse<HotVotePreviewsResponse>> findHotVotes(
            @RequestParam(name = "size", required = false, defaultValue = "3") final Integer size
    ) {
        HotVotePreviewsResponse response = hotVoteService.findHotVotes(null, size);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}