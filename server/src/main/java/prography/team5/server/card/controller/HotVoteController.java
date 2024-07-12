package prography.team5.server.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.auth.controller.AuthRequired;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.card.HotVoteApiDocs;
import prography.team5.server.card.service.HotVoteService;
import prography.team5.server.card.service.dto.CategoryHotVotePreviewsResponse;
import prography.team5.server.card.service.dto.HotVotePreviewsResponse;
import prography.team5.server.common.CommonApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/votes/hot")
public class HotVoteController implements HotVoteApiDocs {

    private final HotVoteService hotVoteService;

    @GetMapping
    public ResponseEntity<CommonApiResponse<HotVotePreviewsResponse>> findHotVotes(
            @AuthRequired(required = false) Accessor accessor,
            @RequestParam(name = "size", required = false, defaultValue = "3") final Integer size
    ) {
        HotVotePreviewsResponse response = hotVoteService.findHotVotes(size, accessor);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CommonApiResponse<CategoryHotVotePreviewsResponse>> findHotVotesByCategoryId(
            @AuthRequired(required = false) Accessor accessor,
            @PathVariable(name = "categoryId") final Long categoryId,
            @RequestParam(name = "size", required = false, defaultValue = "3") final Integer size
    ) {
        CategoryHotVotePreviewsResponse response = hotVoteService.findHotVotesByCategoryId(size, categoryId, accessor);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}
