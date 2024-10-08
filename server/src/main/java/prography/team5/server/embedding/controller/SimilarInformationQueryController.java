package prography.team5.server.embedding.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.common.InformationWrapper;
import prography.team5.server.embedding.SimilarInformationApiDocs;
import prography.team5.server.embedding.service.SimilarInformationQueryService;
import prography.team5.server.embedding.service.dto.SimilarInformationResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/similar-information")
public class SimilarInformationQueryController implements SimilarInformationApiDocs {

    private final SimilarInformationQueryService similarInformationQueryService;

    @GetMapping
    public ResponseEntity<CommonApiResponse<InformationWrapper<List<SimilarInformationResponse>>>> findSimilarInformation(
            @RequestParam(value = "category-id") final Long categoryId,
            @RequestParam(value = "vote-id") final Long voteId,
            @RequestParam(value = "size", defaultValue = "3") final int size
    ) {
        final List<SimilarInformationResponse> response = similarInformationQueryService.querySimilarInformation(
                voteId, categoryId, size
        );
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new InformationWrapper<>(response)));
    }
}
