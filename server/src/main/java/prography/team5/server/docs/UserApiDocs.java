package prography.team5.server.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.auth.dto.Accessor;
import prography.team5.server.service.dto.NicknameRequest;
import prography.team5.server.service.dto.UserResponse;
import prography.team5.server.service.dto.UserTypeRequest;

@Tag(name = "2. 유저 정보", description = "유저 정보 관련 기능입니다.")
public interface UserApiDocs {

    @Operation(
            summary = "유저 정보 조회 API",
            description = "유저 정보(조회 항목: 닉네임)를 조회 할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<UserResponse>> findById(@PathVariable(value = "userId") final long userId);

    @Operation(
            summary = "유저 닉네임 수정 API",
            description = "닉네임을 수정 할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "유저 닉네임 수정 성공입니다.")
    ResponseEntity<CommonApiResponse<UserResponse>> updateNickname(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody NicknameRequest nicknameRequest
    );

    @Operation(
            summary = "유저 타입 저장 API",
            description = "유저의 타입을 저장할 수 있습니다. \n\n"
                    + "STUDENT(학생), JOB_SEEKER(취준생), NEW_EMPLOYEE(입사 1~3년차 직장인), OTHER(기타)"
    )
    @ApiResponse(responseCode = "200", description = "유저 닉네임 수정 성공입니다.")
    ResponseEntity<CommonApiResponse<UserResponse>> updateUserType(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody UserTypeRequest userTypeRequest
    );
}
