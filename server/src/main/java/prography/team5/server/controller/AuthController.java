package prography.team5.server.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.auth.AuthService;
import prography.team5.server.service.auth.dto.EmailRequest;
import prography.team5.server.service.auth.dto.LoginResponse;

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

    @PostMapping("/login")
    public ResponseEntity<CommonApiResponse<LoginResponse>> login(@RequestBody final EmailRequest emailRequest) {
        final LoginResponse response = authService.login(emailRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    //todo: refreshToken으로 accessToken 재발급
}
