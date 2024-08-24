package prography.team5.server.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.auth.service.dto.JoinRequest;
import prography.team5.server.auth.service.dto.WithdrawRequest;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.auth.AuthApiDocs;
import prography.team5.server.auth.service.AuthService;
import prography.team5.server.auth.service.dto.AccessTokenResponse;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.auth.service.dto.EmailRequest;
import prography.team5.server.auth.service.dto.LoginResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApiDocs {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<CommonApiResponse<Void>> join(@RequestBody final JoinRequest joinRequest) {
        authService.joinNewUser(joinRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonApiResponse<LoginResponse>> login(@RequestBody final EmailRequest emailRequest) {
        final LoginResponse response = authService.login(emailRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<CommonApiResponse<AccessTokenResponse>> refreshAccessToken(
            @CookieValue(value = "Refresh", required = false) final String refreshToken
    ) {
        final AccessTokenResponse response = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping("/test")
    public ResponseEntity<CommonApiResponse<Void>> test(@AuthRequired final Accessor accessor) {
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<CommonApiResponse<Void>> withdraw(
            @AuthRequired final Accessor accessor,
            @RequestBody final WithdrawRequest withdrawRequest
    ) {
        authService.withdraw(accessor, withdrawRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
