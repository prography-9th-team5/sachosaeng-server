package prography.team5.server.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.docs.InformationApiDocs;
import prography.team5.server.service.dto.InformationIdResponse;
import prography.team5.server.service.dto.InformationRequest;
import prography.team5.server.service.dto.InformationResponse;
import prography.team5.server.service.InformationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/information")
public class InformationController implements InformationApiDocs {

    private final InformationService informationService;

    @GetMapping("/{informationId}")
    public ResponseEntity<CommonApiResponse<InformationResponse>> findByInformationId(@PathVariable(value = "informationId") final long informationId) {
        final InformationResponse response = informationService.findByInformationId(informationId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PostMapping
    public ResponseEntity<CommonApiResponse<InformationIdResponse>> create(@RequestBody final InformationRequest informationRequest) {
        final InformationIdResponse response = informationService.create(informationRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping
    public ResponseEntity<CommonApiResponse<List<InformationResponse>>> findAll(
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "category-id", required = false) final Long categoryId,
            @RequestParam(name = "page-size", required = false) final Integer pageSize
    ) {
        final List<InformationResponse> response = informationService.findAll(cursor, categoryId, pageSize);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}
