package prography.team5.server.card;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import prography.team5.server.auth.controller.AuthRequired;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.card.service.dto.SimpleVoteWithCategoryResponse;
import prography.team5.server.common.CommonApiResponse;

@Tag(name = "07. 오늘의 투표", description = "오늘 투표 관련 기능입니다.")
public interface DailyVoteApiDocs {

    @Operation(
            summary = "오늘의 투표 조회 API",
            description = "홈화면에 노출되는 오늘의 투표 정보를 조회합니다. \n\n"
                    + "응답에 포함된 voteId를 이용해 투표 상세(/votes/{voteId})를 조회할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "오늘의 투표 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<SimpleVoteWithCategoryResponse>> findTodayVote(
            @Parameter(hidden = true) Accessor accessor
    );
}
