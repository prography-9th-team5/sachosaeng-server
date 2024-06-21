package prography.team5.server.category;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.category.service.dto.CategoryIdResponse;
import prography.team5.server.category.service.dto.CategoryRequest;
import prography.team5.server.category.service.dto.CategoryResponse;

@Tag(name = "3. 카테고리", description = "카테고리 관련 기능입니다.")
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
            summary = "카테고리 전체 조회 API",
            description = "전체 카테고리를 조회 할 수 있습니다."
                    + "\n backgroundColor와 textColor는 임시 값을 넣어둔 상태입니다."
    )
    @ApiResponse(responseCode = "200", description = "카테고리 전체 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<List<CategoryResponse>>> findAll();

    @Hidden
    @Operation(
            summary = "[Admin] 카테고리 추가 API",
            description = "카테고리를 추가할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "카테고리 추가 성공입니다.")
    ResponseEntity<CommonApiResponse<CategoryIdResponse>> add(final CategoryRequest categoryRequest);
}
