package prography.team5.server.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.dto.CardIdResponse;
import prography.team5.server.service.dto.CardRequest;
import prography.team5.server.service.dto.CardResponse;

@Tag(name = "4. 카드 -> 아직 투표기능 미반영", description = "카드 관련 기능입니다.")
public interface CardApiDocs {

    @Operation(
            summary = "단일 카드 조회 API",
            description = "카드 id로 해당 카드를 조회할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "카드 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<CardResponse>> findByCardId(@PathVariable(value = "cardId") final long cardId);

    @Operation(
            summary = "[Admin] 카드 추가 API",
            description = "카드를 추가할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "카드 추가 성공입니다.")
    ResponseEntity<CommonApiResponse<CardIdResponse>> add(final CardRequest cardRequest);

    @Operation(
            summary = "카드 리스트 전체 조회 API",
            description = "카드 리스트를 전체 조회할 수 있습니다. 카드는 최신순으로 조회됩니다. \n\n"
                    + "cursor 값으로 마지막 cardId를 전달하면 해당 cardId 이전의 카드를 10개 조회할 수 있습니다. (cursor는 포함X) \n\n"
                    + "cursor 값을 전달하지 않으면 가장 최근에 생성된 카드 10개를 조회합니다.\n\n"
                    + "category-id 값에 조회하고 싶은 categoryId를 넣으면 특정 카테고리의 카드들만 조회됩니다. \n\n"
    )
    @ApiResponse(responseCode = "200", description = "카드 리스트 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<List<CardResponse>>> findAll(
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "category-id", required = false) final Long categoryId
    );
}
