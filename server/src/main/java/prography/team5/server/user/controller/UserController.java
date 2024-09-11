package prography.team5.server.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.auth.controller.AuthRequired;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.common.EmptyData;
import prography.team5.server.user.UserApiDocs;
import prography.team5.server.user.service.UserService;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.user.service.dto.NicknameRequest;
import prography.team5.server.user.service.dto.UserResponse;
import prography.team5.server.user.service.dto.UserTypeRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserApiDocs {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<CommonApiResponse<UserResponse>> findById(
            @AuthRequired Accessor accessor
    ) {
        final UserResponse response = userService.find(accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PutMapping("/nickname")
    public ResponseEntity<CommonApiResponse<EmptyData>> updateNickname(
            @AuthRequired Accessor accessor,
            @RequestBody NicknameRequest nicknameRequest
    ) {
        userService.updateNickname(accessor.id(), nicknameRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @PutMapping("/user-type")
    public ResponseEntity<CommonApiResponse<EmptyData>> updateUserType(
            @AuthRequired Accessor accessor,
            @RequestBody UserTypeRequest userTypeRequest
    ) {
        userService.updateUserType(accessor.id(), userTypeRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }
}
