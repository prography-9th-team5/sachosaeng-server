package prography.team5.server.card;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import prography.team5.server.card.service.dto.HotVotePreviewsResponse;
import prography.team5.server.common.CommonApiResponse;

@Tag(name = "6. 인기 투표", description = "인기 투표 관련 기능입니다.")
public interface HotVoteApiDocs {

    @Operation(
            summary = "[홈화면] 인기 투표 3개 조회 API",
            description = """
                        전체 카테고리를 통틀어 인기투표 3개를 조회합니다. \n\n
                        투표 참여자수가 10보다 작을 경 participantCount는 null로 반환됩니다.
                        """
    )
    @ApiResponse(responseCode = "200", description = "인기 투표 목록 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<HotVotePreviewsResponse>> findHotVotes(
            @RequestParam(name = "size", required = false, defaultValue = "3") final Integer size
    );
}
