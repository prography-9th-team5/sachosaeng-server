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
import prography.team5.server.admin.service.InformationAdminService;
import prography.team5.server.admin.service.dto.FullInformationResponse;
import prography.team5.server.card.service.dto.InformationIdResponse;
import prography.team5.server.admin.service.dto.InformationCreationRequest;
import prography.team5.server.category.service.CategoryService;
import prography.team5.server.category.service.dto.CategoryResponse;
import prography.team5.server.common.CommonApiResponse;

@RestController
@RequestMapping("/admin/information")
@RequiredArgsConstructor
public class InformationAdminController {

    private final InformationAdminService informationAdminService;
    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView information(ModelAndView modelAndView) {
        final List<FullInformationResponse> information = informationAdminService.findAll();
        modelAndView.addObject("information", information);
        final List<CategoryResponse> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);
        modelAndView.setViewName("information");
        return modelAndView;
    }

    @Hidden
    @GetMapping("/{informationId}")
    public ResponseEntity<CommonApiResponse<FullInformationResponse>> findByInformationId(
            @PathVariable(value = "informationId") final long informationId) {
        final FullInformationResponse response = informationAdminService.findByInformationId(informationId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @Hidden
    @PostMapping
    public ResponseEntity<CommonApiResponse<InformationIdResponse>> create(
            @RequestBody final InformationCreationRequest informationCreationRequest) {
        final InformationIdResponse response = informationAdminService.create(informationCreationRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @Hidden
    @PutMapping("/{informationId}")
    public ResponseEntity<CommonApiResponse<Void>> update(
            @PathVariable(value = "informationId") final long informationId,
            @RequestBody final InformationCreationRequest informationCreationRequest) {
        informationAdminService.modifyById(informationId, informationCreationRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
