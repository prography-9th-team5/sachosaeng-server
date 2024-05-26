package prography.team5.server.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.auth.dto.Accessor;
import prography.team5.server.service.dto.CategoryResponse;

@Tag(name = "4. 관심 카테고리", description = "관심 카테고리 관련 기능입니다..")
public interface BookmarkCategoryApiDocs {

    @Operation(
            summary = "관심 카테고리 조회 API",
            description = "유저의 관심 카테고리를 조회할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "관심 카테고리 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<List<CategoryResponse>>> findAllByUserId(@Parameter(hidden = true) Accessor accessor);
}
