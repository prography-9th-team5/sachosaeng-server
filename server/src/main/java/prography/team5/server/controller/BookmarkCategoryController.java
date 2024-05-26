package prography.team5.server.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.controller.auth.AuthRequired;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.docs.BookmarkCategoryApiDocs;
import prography.team5.server.service.BookmarkCategoryService;
import prography.team5.server.service.auth.dto.Accessor;
import prography.team5.server.service.dto.BookmarkCategoryRequest;
import prography.team5.server.service.dto.CategoryResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookmark-categories")
public class BookmarkCategoryController implements BookmarkCategoryApiDocs {

    private final BookmarkCategoryService bookmarkCategoryService;

    @GetMapping
    public ResponseEntity<CommonApiResponse<List<CategoryResponse>>> findAllByUserId(@AuthRequired Accessor accessor) {
        final List<CategoryResponse> response = bookmarkCategoryService.findAllByUserId(accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PutMapping
    public ResponseEntity<CommonApiResponse<Void>> updateByUserId(
            @AuthRequired Accessor accessor,
            @RequestBody BookmarkCategoryRequest bookmarkCategoryRequest
    ) {
        bookmarkCategoryService.updateAllByUserId(accessor.id(), bookmarkCategoryRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
