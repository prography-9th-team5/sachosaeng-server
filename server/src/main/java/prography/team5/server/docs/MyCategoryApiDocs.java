package prography.team5.server.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.auth.dto.Accessor;
import prography.team5.server.service.dto.CategoryWithThumbnailResponse;
import prography.team5.server.service.dto.MyCategoryRequest;

@Tag(name = "4. 관심 카테고리", description = "관심 카테고리 관련 기능입니다.")
public interface MyCategoryApiDocs {

    @Operation(
            summary = "관심 카테고리 조회 API",
            description = "유저의 관심 카테고리를 조회할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "관심 카테고리 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<List<CategoryWithThumbnailResponse>>> findAllByUserId(
            @Parameter(hidden = true) Accessor accessor
    );

    @Operation(
            summary = "관심 카테고리 갱신 API",
            description = "유저의 관심 카테고리를 갱신할 수 있습니다. body에 담아 보낸 카테고리들로 관심 카테고리가 전체 갱신 됩니다."
    )
    @ApiResponse(responseCode = "200", description = "관심 카테고리 갱신 성공입니다.")
    ResponseEntity<CommonApiResponse<Void>> updateByUserId(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody MyCategoryRequest myCategoryRequest
    );

/*    @Operation(
            summary = "관심 카테고리에 속한 정보 카드 리스트 조회 API",
            description = "관심 카테고리의 정보들을 조회할 수 있습니다. 정보는 최신순으로 조회됩니다. \n\n"
                    + "cursor 값으로 마지막 informationId를 전달하면 해당 informationId 이전의 카드를 10개 조회할 수 있습니다. (cursor는 포함X) \n\n"
                    + "cursor 값을 전달하지 않으면 가장 최근에 생성된 정보 10개를 조회합니다. \n\n"
    )
    @ApiResponse(responseCode = "200", description = "관심 카테고리 갱신 성공입니다.")
    ResponseEntity<CommonApiResponse<List<InformationResponse>>> findAllInformationByUserId(
            @Parameter(hidden = true) Accessor accessor,
            @RequestParam(name = "cursor", required = false) final Long cursor
    );*/
}
