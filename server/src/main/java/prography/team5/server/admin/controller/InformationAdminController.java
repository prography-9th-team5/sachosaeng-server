package prography.team5.server.admin.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import prography.team5.server.admin.service.InformationAdminService;
import prography.team5.server.admin.service.dto.FullInformationResponse;
import prography.team5.server.card.service.dto.InformationIdResponse;
import prography.team5.server.admin.service.dto.InformationCreationRequest;
import prography.team5.server.common.CommonApiResponse;

@RestController
@RequestMapping("/admin/information")
@RequiredArgsConstructor
public class InformationAdminController {

    private final InformationAdminService informationAdminService;

    @GetMapping
    public ModelAndView information(ModelAndView modelAndView) {
        //todo: 게시글 많을때도 옛날 게시글 조회되게!
        final List<FullInformationResponse> information = informationAdminService.findAll(null,100);
        modelAndView.addObject("information", information);
        modelAndView.setViewName("information");
        return modelAndView;
    }
    @GetMapping("/{informationId}")
    public ResponseEntity<CommonApiResponse<FullInformationResponse>> findByInformationId(
            @PathVariable(value = "informationId") final long informationId) {
        final FullInformationResponse response = informationAdminService.findByInformationId(informationId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PostMapping
    public ResponseEntity<CommonApiResponse<InformationIdResponse>> create(
            @RequestBody final InformationCreationRequest informationCreationRequest) {
        final InformationIdResponse response = informationAdminService.create(informationCreationRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}
