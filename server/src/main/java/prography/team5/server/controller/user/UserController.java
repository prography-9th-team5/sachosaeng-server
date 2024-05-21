package prography.team5.server.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.docs.UserApiDocs;
import prography.team5.server.service.user.UserService;
import prography.team5.server.service.user.dto.UserResponse;

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
}
