package prography.team5.server.mycategory.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.auth.controller.AuthRequired;
import prography.team5.server.common.CategoriesWrapper;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.common.EmptyData;
import prography.team5.server.mycategory.MyCategoryApiDocs;
import prography.team5.server.mycategory.service.MyCategoryService;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.category.service.dto.CategoryResponse;
import prography.team5.server.mycategory.service.dto.MyCategoryRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/my-categories")
public class MyCategoryController implements MyCategoryApiDocs {

    private final MyCategoryService myCategoryService;

    @GetMapping
    public ResponseEntity<CommonApiResponse<CategoriesWrapper<List<CategoryResponse>>>> findAllByUserId(@AuthRequired Accessor accessor) {
        final List<CategoryResponse> response = myCategoryService.findAllByUserId(accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new CategoriesWrapper<>(response)));
    }

    @PutMapping
    public ResponseEntity<CommonApiResponse<EmptyData>> updateByUserId(
            @AuthRequired Accessor accessor,
            @RequestBody MyCategoryRequest myCategoryRequest
    ) {
        myCategoryService.updateAllByUserId(accessor.id(), myCategoryRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }
}
