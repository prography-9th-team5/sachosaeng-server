package prography.team5.server.card;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.card.service.dto.InformationIdResponse;
import prography.team5.server.admin.service.dto.InformationCreationRequest;
import prography.team5.server.card.service.dto.InformationResponse;

@Tag(name = "6. 정보 카드", description = "정보 제공 카드 관련 기능입니다.")
public interface InformationApiDocs {

    @Hidden
    @Operation(
            summary = "단일 정보 카드 조회 API",
            description = "정보 id로 해당 정보를 조회할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "정보 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<InformationResponse>> findByInformationId(@PathVariable(value = "informationId") final long informationId);

    @Hidden
    @Operation(
            summary = "[Admin] 정보 카드 추가 API",
            description = "정보를 추가할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "정보 추가 성공입니다.")
    ResponseEntity<CommonApiResponse<InformationIdResponse>> create(final InformationCreationRequest informationCreationRequest);

    @Hidden
    @Operation(
            summary = "[임시] 정보 카드 리스트 전체 조회 API",
            description = "정보 리스트를 전체 조회할 수 있습니다. 정보는 최신순으로 조회됩니다. \n\n"
                    + "cursor 값으로 마지막 informationId를 전달하면 해당 informationId 이전의 정보를 10개 조회할 수 있습니다. (cursor는 포함X) \n\n"
                    + "cursor 값을 전달하지 않으면 가장 최근에 생성된 정보 10개를 조회합니다.\n\n"
                    + "category-id 값에 조회하고 싶은 categoryId를 넣으면 해당 카테고리의 정보들만 조회됩니다. \n\n"
                    + "page-size 값에 조회하고 싶은 정보의 개수를 적으면 해당 개수만큼의 정보들이 조회됩니다. (default 10)"
    )
    @ApiResponse(responseCode = "200", description = "정보 리스트 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<List<InformationResponse>>> findAll(
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "category-id", required = false) final Long categoryId,
            @RequestParam(name = "page-size", required = false) final Integer pageSize
    );
}
