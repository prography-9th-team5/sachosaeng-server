package prography.team5.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.controller.auth.AuthRequired;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.docs.UserApiDocs;
import prography.team5.server.service.auth.dto.Accessor;
import prography.team5.server.service.UserService;
import prography.team5.server.service.dto.NicknameRequest;
import prography.team5.server.service.dto.UserResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController implements UserApiDocs {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<CommonApiResponse<UserResponse>> findById(@PathVariable(value = "userId") final long userId) {
        final UserResponse response = userService.find(userId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PutMapping("/nickname")
    public ResponseEntity<CommonApiResponse<UserResponse>> updateNickname(
            @AuthRequired Accessor accessor,
            @RequestBody NicknameRequest nicknameRequest
    ) {
        userService.updateNickname(accessor.id(), nicknameRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
