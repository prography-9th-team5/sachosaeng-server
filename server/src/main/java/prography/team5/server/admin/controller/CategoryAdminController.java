package prography.team5.server.admin.controller;

import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import prography.team5.server.admin.service.CategoryAdminService;
import prography.team5.server.admin.service.dto.CategoryUpdateRequest;
import prography.team5.server.admin.service.dto.CategoryWithUserTypesResponse;
import prography.team5.server.category.service.dto.CategoryIdResponse;
import prography.team5.server.category.service.dto.CategoryRequest;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.common.EmptyData;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryAdminService categoryAdminService;

    @GetMapping
    public ModelAndView categories(ModelAndView modelAndView) {
        final List<CategoryWithUserTypesResponse> categories = categoryAdminService.findAll();
        modelAndView.addObject("categories", categories);
        modelAndView.setViewName("categories");
        return modelAndView;
    }

    @Hidden
    @PostMapping
    public ResponseEntity<CommonApiResponse<CategoryIdResponse>> create(
            @RequestBody final CategoryRequest categoryRequest
    ) {
        final CategoryIdResponse response = categoryAdminService.create(categoryRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @Hidden
    @PutMapping("/{categoryId}")
    public ResponseEntity<CommonApiResponse<EmptyData>> update(
            @PathVariable(value = "categoryId") final Long categoryId,
            @RequestBody final CategoryUpdateRequest categoryUpdateRequest
            ) {
        categoryAdminService.update(categoryId, categoryUpdateRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }
}
