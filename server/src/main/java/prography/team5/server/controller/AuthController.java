package prography.team5.server.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.AuthService;
import prography.team5.server.service.dto.EmailRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<CommonApiResponse<Void>> join(@RequestBody final EmailRequest emailRequest) {
        final long userId = authService.joinNewUser(emailRequest);
        return ResponseEntity.created(URI.create("/users/" + userId))
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
