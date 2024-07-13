package prography.team5.server.embedding;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.card.service.dto.SimpleVoteWithCategoryResponse;
import prography.team5.server.common.CommonApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    @GetMapping("/ai/embedding")
    public ResponseEntity<CommonApiResponse<SimpleVoteWithCategoryResponse>> embed() {
        embeddingService.embed();
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
