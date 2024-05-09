package prography.team5.server.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.auth.dto.AccessTokenResponse;
import prography.team5.server.service.auth.dto.EmailRequest;
import prography.team5.server.service.auth.dto.LoginResponse;
import prography.team5.server.service.auth.dto.VerifiedUser;

@Tag(name = "1. 인증", description = "인증 관련 기능입니다.")
public interface AuthApiDocs {

    @Operation(
            summary = "회원 가입 API",
            description = "이메일을 통해 회원가입을 할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "회원가입 성공입니다.")
    ResponseEntity<CommonApiResponse<Void>> join(final EmailRequest emailRequest);

    @Operation(
            summary = "로그인 API",
            description = "이메일을 통해 로그인을 할 수 있습니다. 로그인을 할 때 엑세스 토큰과 리프레시 토큰을 발급합니다."
    )
    @ApiResponse(responseCode = "200", description = "로그인 성공입니다.")
    ResponseEntity<CommonApiResponse<LoginResponse>> login(final EmailRequest emailRequest);

    @Operation(
            summary = "엑세스 토큰 재발급 API",
            description = "리프레시 토큰으로 엑세스 토큰을 재발급 할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "엑세스 토큰 재발급 성공입니다.")
    ResponseEntity<CommonApiResponse<AccessTokenResponse>> refreshAccessToken(
            @CookieValue(value = "Refresh", required = false) final String refreshToken
    );

    @Operation(
            summary = "인증 테스트 API",
            description = "헤더에 Bearer {엑세스 토큰}을 담아 보냈을 때 응답이 성공하는지 테스트 할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "인증 성공입니다.")
    ResponseEntity<CommonApiResponse<Void>> test(@Parameter(hidden = true) final VerifiedUser verifiedUser);
}