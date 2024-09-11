package prography.team5.server.mycategory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import prography.team5.server.common.CategoriesWrapper;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.category.service.dto.CategoryResponse;
import prography.team5.server.common.EmptyData;
import prography.team5.server.mycategory.service.dto.MyCategoryRequest;

@Tag(name = "04. 관심 카테고리", description = "관심 카테고리 관련 기능입니다.")
public interface MyCategoryApiDocs {

    @Operation(
            summary = "[인증 토큰 필요] 관심 카테고리 조회 API -> []를 {}로 감쌌어요!!",
            description = "유저의 관심 카테고리를 조회할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "관심 카테고리 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<CategoriesWrapper<List<CategoryResponse>>>> findAllByUserId(
            @Parameter(hidden = true) Accessor accessor
    );

    @Operation(
            summary = "[인증 토큰 필요] 관심 카테고리 갱신 API",
            description = "유저의 관심 카테고리를 갱신할 수 있습니다. body에 담아 보낸 카테고리들로 관심 카테고리가 전체 갱신 됩니다."
    )
    @ApiResponse(responseCode = "200", description = "관심 카테고리 갱신 성공입니다.")
    ResponseEntity<CommonApiResponse<EmptyData>> updateByUserId(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody MyCategoryRequest myCategoryRequest
    );
}
