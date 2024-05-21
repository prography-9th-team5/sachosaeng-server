package prography.team5.server.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.user.dto.UserResponse;

@Tag(name = "3. 유저 정보", description = "유저 정보 관련 기능입니다.")
public interface UserApiDocs {

    @Operation(
            summary = "유저 정보 조회 API",
            description = "유저 정보를 조회 할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<UserResponse>> findById(final long userId);
}
