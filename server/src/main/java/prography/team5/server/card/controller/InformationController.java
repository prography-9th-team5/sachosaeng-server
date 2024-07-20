package prography.team5.server.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.card.InformationApiDocs;
import prography.team5.server.card.service.InformationService;
import prography.team5.server.card.service.dto.InformationResponse;
import prography.team5.server.common.CommonApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/information")
public class InformationController implements InformationApiDocs {

    private final InformationService informationService;

    @GetMapping("/{informationId}")
    public ResponseEntity<CommonApiResponse<InformationResponse>> findByInformationId(
            @PathVariable(value = "informationId") final long informationId,
            @RequestParam(value = "category-id", required = false) final Long categoryId
    ) {
        final InformationResponse response = informationService.find(informationId, categoryId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}
