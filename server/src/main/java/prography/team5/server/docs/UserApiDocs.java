package prography.team5.server.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import prography.team5.server.controller.auth.AuthRequired;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.auth.dto.Accessor;
import prography.team5.server.service.user.dto.NicknameRequest;
import prography.team5.server.service.user.dto.UserResponse;

@Tag(name = "3. 유저 정보", description = "유저 정보 관련 기능입니다.")
public interface UserApiDocs {

    @Operation(
            summary = "유저 정보 조회 API",
            description = "유저 정보(조회 항목: 닉네임)를 조회 할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<UserResponse>> findById(final long userId);

    @Operation(
            summary = "유저 닉네임 수정 API",
            description = "닉네임을 수정 할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "유저 닉네임 수정 성공입니다.")
    ResponseEntity<CommonApiResponse<UserResponse>> updateNickname(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody NicknameRequest nicknameRequest
    );
}