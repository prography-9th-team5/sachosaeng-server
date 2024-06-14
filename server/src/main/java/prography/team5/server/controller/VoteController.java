package prography.team5.server.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.controller.auth.AuthRequired;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.docs.VoteApiDocs;
import prography.team5.server.service.VoteService;
import prography.team5.server.service.auth.dto.Accessor;
import prography.team5.server.service.dto.VoteIdResponse;
import prography.team5.server.service.dto.VoteRequest;
import prography.team5.server.service.dto.VoteResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/votes")
public class VoteController implements VoteApiDocs {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<CommonApiResponse<VoteIdResponse>> create(
            @AuthRequired Accessor accessor,
            @RequestBody final VoteRequest voteRequest
    ) {
        final VoteIdResponse response = voteService.create(voteRequest, accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping("/{voteId}")
    public ResponseEntity<CommonApiResponse<VoteResponse>> findByVoteId(
            @PathVariable(value = "voteId") final long voteId) {
        final VoteResponse response = voteService.findByVoteId(voteId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping
    public ResponseEntity<CommonApiResponse<List<VoteResponse>>> findAll(
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "category-id", required = false) final Long categoryId,
            @RequestParam(name = "page-size", required = false) final Integer pageSize
    ) {
        final List<VoteResponse> response = voteService.findAll(cursor, categoryId, pageSize);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}