package prography.team5.server.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.category.dto.CategoryRequest;
import prography.team5.server.service.category.dto.CategoryResponse;

@Tag(name = "3. 카테고리", description = "카테고리 관련 기능입니다.")
public interface CategoryApiDocs {

    @Operation(
            summary = "카테고리 전체 조회 API",
            description = "전체 카테고리를 조회 할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "카테고리 전체 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<List<CategoryResponse>>> findAll();

    @Operation(
            summary = "[Admin] 카테고리 추가 API",
            description = "카테고리를 추가할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "카테고리 추가 성공입니다.")
    ResponseEntity<CommonApiResponse<Void>> add(final CategoryRequest categoryRequest);
}
