package prography.team5.server.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.dto.CardIdResponse;
import prography.team5.server.service.dto.CardRequest;
import prography.team5.server.service.dto.CardResponse;

@Tag(name = "4. 카드", description = "카드 관련 기능입니다.")
public interface CardApiDocs {

    @Operation(
            summary = "단일 카드 조회 API",
            description = "카드 id로 해당 카드를 조회할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "카드 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<CardResponse>> findByCardId(final long cardId);

    @Operation(
            summary = "[Admin] 카드 추가 API",
            description = "카드를 추가할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "카드 추가 성공입니다.")
    ResponseEntity<CommonApiResponse<CardIdResponse>> add(final CardRequest cardRequest);
}
