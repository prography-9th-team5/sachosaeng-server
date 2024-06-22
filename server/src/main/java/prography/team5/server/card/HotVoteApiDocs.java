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
                        전체 카테고리를 통틀어 인기투표 3개를 조회합니다. \n
                        현재는 최신순으로 3개 조회하는 중, 인기 투표 노출 로직은 추후 수정 예정입니다.
                        """
    )
    @ApiResponse(responseCode = "200", description = "인기 투표 목록 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<HotVotePreviewsResponse>> findHotVotes(
            @RequestParam(name = "size", required = false, defaultValue = "3") final Integer size
    );
}
