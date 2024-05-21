package prography.team5.server.controller.cateogry;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.docs.CategoryApiDocs;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.category.CategoryService;
import prography.team5.server.service.category.dto.CategoryRequest;
import prography.team5.server.service.category.dto.CategoryResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController implements CategoryApiDocs {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CommonApiResponse<List<CategoryResponse>>> findAll() {
        final List<CategoryResponse> response = categoryService.findAll();
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    //todo: 어드민 페이지
    @PostMapping
    public ResponseEntity<CommonApiResponse<Void>> add(@RequestBody final CategoryRequest categoryRequest) {
        categoryService.add(categoryRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
