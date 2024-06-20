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
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.mycategory.MyCategoryApiDocs;
import prography.team5.server.mycategory.service.MyCategoryService;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.category.service.dto.CategoryResponse;
import prography.team5.server.mycategory.service.dto.MyCategoryRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/my-categories")
public class MyCategoryController implements MyCategoryApiDocs {

    private final MyCategoryService myCategoryService;

    @GetMapping
    public ResponseEntity<CommonApiResponse<List<CategoryResponse>>> findAllByUserId(@AuthRequired Accessor accessor) {
        final List<CategoryResponse> response = myCategoryService.findAllByUserId(accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PutMapping
    public ResponseEntity<CommonApiResponse<Void>> updateByUserId(
            @AuthRequired Accessor accessor,
            @RequestBody MyCategoryRequest myCategoryRequest
    ) {
        myCategoryService.updateAllByUserId(accessor.id(), myCategoryRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }

/*    @GetMapping("/information")
    public ResponseEntity<CommonApiResponse<List<InformationResponse>>> findAllInformationByUserId(
            @AuthRequired Accessor accessor,
            @RequestParam(name = "cursor", required = false) final Long cursor
    ) {
        List<InformationResponse> response = myCategoryService.findAllInformationByUserId(accessor.id(), cursor);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }*/
}
