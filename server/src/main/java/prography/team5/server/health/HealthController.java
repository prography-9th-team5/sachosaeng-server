package prography.team5.server.health;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.common.CommonApiResponse;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Hidden
    @GetMapping
    public ResponseEntity<CommonApiResponse<Void>> checkHealth() {
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
