package prography.team5.server.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import prography.team5.server.category.service.dto.CategoryResponse;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.common.CategoriesWrapper;

@Tag(name = "03. 카테고리", description = "카테고리 관련 기능입니다.")
public interface CategoryApiDocs {

    @Operation(
            summary = "단일 카테고리 조회 API",
            description = "단일 카테고리에 대한 정보(카테고리 이름, 아이콘 Url, 백그라운드 컬러, 텍스트 컬러 등) 조회 할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "단일 카테고리 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<CategoryResponse>> findById(
            @PathVariable(value = "categoryId") final long categoryId
    );

    @Operation(
            summary = "카테고리 전체 조회 API -> []를 {}로 감쌌어요!!",
            description = "전체 카테고리를 조회 할 수 있습니다."
                    + "\n backgroundColor는 임시 값을 넣어둔 상태입니다."
    )
    @ApiResponse(responseCode = "200", description = "카테고리 전체 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<CategoriesWrapper<List<CategoryResponse>>>> findAll();
}
