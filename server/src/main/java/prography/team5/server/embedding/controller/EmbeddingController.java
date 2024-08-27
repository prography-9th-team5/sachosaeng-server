package prography.team5.server.embedding.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.embedding.service.EmbeddingService;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    // 스케줄링으로 임베딩이 안되었을때 호출하는 용도임
    @Hidden
    @GetMapping("/ai/embedding")
    public ResponseEntity<CommonApiResponse<Void>> embed() {
        embeddingService.embed();
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
