package prography.team5.server.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.auth.dto.Accessor;
import prography.team5.server.service.dto.VoteIdResponse;
import prography.team5.server.service.dto.VoteRequest;
import prography.team5.server.service.dto.VoteResponse;

@Tag(name = "5. 투표 카드", description = "투표 카드 관련 기능입니다.")
public interface VoteApiDocs {

    @Operation(
            summary = "투표 카드 추가 API",
            description = "투표를 추가할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "투표 추가 성공입니다.")
    ResponseEntity<CommonApiResponse<VoteIdResponse>> create(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody final VoteRequest voteRequest
    );

    @Operation(
            summary = "단일 투표 카드 조회 API",
            description = "투표 id로 해당 투표를 조회할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "투표 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<VoteResponse>> findByVoteId(@PathVariable(value = "voteId") final long voteId);
}
