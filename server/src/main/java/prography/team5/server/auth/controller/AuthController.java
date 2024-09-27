package prography.team5.server.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.auth.AuthApiDocs;
import prography.team5.server.auth.service.AuthService;
import prography.team5.server.auth.service.dto.AppleTokenResponse;
import prography.team5.server.auth.service.dto.JoinResponse;
import prography.team5.server.auth.service.dto.TokenRequest;
import prography.team5.server.auth.service.dto.TokenResponse;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.auth.service.dto.EmailRequest;
import prography.team5.server.auth.service.dto.JoinRequest;
import prography.team5.server.auth.service.dto.LoginResponse;
import prography.team5.server.auth.service.dto.WithdrawRequest;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.common.EmptyData;
import prography.team5.server.user.domain.SocialType;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApiDocs {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<CommonApiResponse<JoinResponse>> join(
            @RequestBody final JoinRequest joinRequest,
            @RequestParam(value = "type", defaultValue = "DEFAULT", required = false) SocialType socialType
    ) {
        final JoinResponse response = authService.joinNewUser(joinRequest, socialType);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonApiResponse<LoginResponse>> login(
            @RequestBody final EmailRequest emailRequest,
            @RequestParam(value = "type", defaultValue = "DEFAULT", required = false) SocialType socialType,
            @RequestHeader(value = "X-Device", required = false) final String device
    ) {
        final LoginResponse response = authService.login(emailRequest, socialType, device);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PostMapping("/login-by-token")
    public ResponseEntity<CommonApiResponse<LoginResponse>> loginByToken(
            @RequestBody final TokenRequest tokenRequest,
            @RequestHeader(value = "X-Device", required = false) final String device
    ) {
        final LoginResponse response = authService.loginByToken(tokenRequest, device);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<CommonApiResponse<TokenResponse>> refreshAccessToken(
            @CookieValue(value = "Refresh") final String refreshToken,
            @RequestHeader(value = "X-Device", required = false) final String device
    ) {
        final TokenResponse response = authService.refreshAccessToken(refreshToken, device);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping("/test")
    public ResponseEntity<CommonApiResponse<EmptyData>> test(@AuthRequired final Accessor accessor) {
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<CommonApiResponse<EmptyData>> withdraw(
            @AuthRequired final Accessor accessor,
            @RequestBody final WithdrawRequest withdrawRequest
    ) {
        authService.withdraw(accessor, withdrawRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @GetMapping("/apple-token")
    public ResponseEntity<CommonApiResponse<AppleTokenResponse>> createAppleToken(
    ) {
        AppleTokenResponse response = authService.createAppleToken();
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}
