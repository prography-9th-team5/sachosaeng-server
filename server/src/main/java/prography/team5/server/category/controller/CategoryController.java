package prography.team5.server.category.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.category.service.IconData;
import prography.team5.server.category.service.dto.IconResponse;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.category.CategoryApiDocs;
import prography.team5.server.category.service.CategoryService;
import prography.team5.server.category.service.dto.CategoryIdResponse;
import prography.team5.server.category.service.dto.CategoryRequest;
import prography.team5.server.category.service.dto.CategoryResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController implements CategoryApiDocs {

    private final CategoryService categoryService;
    private final IconData iconData;


    @GetMapping("/{categoryId}")
    public ResponseEntity<CommonApiResponse<CategoryResponse>> findById(
            @PathVariable(value = "categoryId") final long categoryId
    ) {
        final CategoryResponse response = categoryService.findById(categoryId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping
    public ResponseEntity<CommonApiResponse<List<CategoryResponse>>> findAll() {
        final List<CategoryResponse> response = categoryService.findAll();
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping("/icon-data/all")
    public ResponseEntity<CommonApiResponse<IconResponse>> findAllIconData() {
        IconResponse response = new IconResponse(
                iconData.getAllIconUrl(),
                iconData.getAllIconBackgroundColor()
        );
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}
