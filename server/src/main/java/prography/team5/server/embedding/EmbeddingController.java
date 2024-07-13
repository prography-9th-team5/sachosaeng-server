package prography.team5.server.embedding;

import io.swagger.v3.oas.annotations.Hidden;
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

    // todo: 주기적으로 api 호출로 임베딩할지? 아니면 정보 콘텐츠가 추가될때마다 알아서 임베딩될지?
    @Hidden
    @GetMapping("/ai/embedding")
    public ResponseEntity<CommonApiResponse<Void>> embed() {
        embeddingService.embed();
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
