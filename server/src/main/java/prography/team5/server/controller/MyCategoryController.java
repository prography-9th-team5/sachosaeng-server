package prography.team5.server.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.controller.auth.AuthRequired;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.docs.MyCategoryApiDocs;
import prography.team5.server.service.MyCategoryService;
import prography.team5.server.service.auth.dto.Accessor;
import prography.team5.server.service.dto.MyCategoryRequest;
import prography.team5.server.service.dto.InformationResponse;
import prography.team5.server.service.dto.CategoryResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookmark-categories")
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
