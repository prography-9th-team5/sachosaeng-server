package prography.team5.server.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import prography.team5.server.auth.service.dto.AccessTokenResponse;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.auth.service.dto.EmailRequest;
import prography.team5.server.auth.service.dto.JoinRequest;
import prography.team5.server.auth.service.dto.LoginResponse;
import prography.team5.server.auth.service.dto.WithdrawRequest;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.user.domain.SocialType;

@Tag(name = "01. 인증", description = "인증 관련 기능입니다.")
public interface AuthApiDocs {

    @Operation(
            summary = "회원 가입 API",
            description = """
                    이메일을 통해 회원가입을 할 수 있습니다. 유저타입: STUDENT(학생), JOB_SEEKER(취준생), NEW_EMPLOYEE(입사 1~3년차 직장인), OTHER(기타)\n
                    애플로 회원가입의 경우 type=APPLE 이라는 추가적인 쿼리 파라미터가 필요합니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "회원가입 성공입니다.")
    ResponseEntity<CommonApiResponse<Void>> join(
            @RequestBody final JoinRequest joinRequest,
            @RequestParam(value = "type", defaultValue = "DEFAULT", required = false) SocialType socialType
    );

    @Operation(
            summary = "로그인 API",
            description = """
                    이메일을 통해 로그인을 할 수 있습니다. 로그인을 할 때 엑세스 토큰과 리프레시 토큰을 발급합니다.\n
                    애플로 로그인의 경우 type=APPLE 이라는 추가적인 쿼리 파라미터가 필요합니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "로그인 성공입니다.")
    ResponseEntity<CommonApiResponse<LoginResponse>> login(
            final EmailRequest emailRequest,
            @RequestParam(value = "type", defaultValue = "DEFAULT", required = false) SocialType socialType
    );

    @Operation(
            summary = "엑세스 토큰 재발급 API -> 스웨거에서 작동 안됨ㅜ",
            description = "Cookie에 Refresh={리프레시 토큰}을 담아 보내면 엑세스 토큰을 재발급 할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "엑세스 토큰 재발급 성공입니다.")
    ResponseEntity<CommonApiResponse<AccessTokenResponse>> refreshAccessToken(
            @Parameter(hidden = true) final String refreshToken
    );

    @Operation(
            summary = "[인증 토큰 필요] 회원 탈퇴 API",
            description = "회원 탈퇴를 할 수 있습니다. 회원 탈퇴 성공시 refresh token은 서버에서 무효화 됩니다. access token은 삭제해주세요."
    )
    @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공입니다.")
    ResponseEntity<CommonApiResponse<Void>> withdraw(
            @Parameter(hidden = true) final Accessor accessor,
            @RequestBody final WithdrawRequest withdrawRequest
    );

    @Operation(
            summary = "[인증 토큰 필요] 인증 테스트 API",
            description = "Authorization 헤더에 Bearer {엑세스 토큰}을 담아 보냈을 때 응답이 성공하는지 테스트 할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "인증 성공입니다.")
    ResponseEntity<CommonApiResponse<Void>> test(@Parameter(hidden = true) final Accessor accessor);
}
