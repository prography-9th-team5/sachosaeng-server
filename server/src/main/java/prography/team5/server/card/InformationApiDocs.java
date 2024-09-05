package prography.team5.server.card;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import prography.team5.server.card.service.dto.InformationResponse;
import prography.team5.server.common.CommonApiResponse;

@Tag(name = "09. 정보 콘텐츠 카드", description = "정보 콘텐츠 카드 관련 기능입니다.")
public interface InformationApiDocs {

    @Operation(
            summary = "단일 정보 카드 조회 API",
            description = """
                    정보 id로 해당 정보를 조회할 수 있습니다.
                                        
                    카드가 2개 이상의 카테고리에 속할 경우를 고려하여,
                    파라미터로 category-id를 넘기면 응답에 해당 category 정보를 넣어 반환합니다.
                    category-id를 넘기지 않아도 카드가 속한 카테고리들 중 하나의 정보를 넣어 반환합니다.
                    
                    subtitle이 없을 경우는 null이 표시됩니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "정보 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<InformationResponse>> findByInformationId(
            @PathVariable(value = "informationId") final long informationId,
            @RequestParam(value = "category-id", required = false) final Long categoryId
    );
}
