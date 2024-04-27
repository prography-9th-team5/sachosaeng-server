package prography.team5.server.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.service.AuthService;
import prography.team5.server.service.dto.JoinRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<CommonApiResponse<Void>> join(@RequestBody JoinRequest joinRequest) {
        authService.joinNewMember(joinRequest);
        return ResponseEntity.created(URI.create("/login")).build();
    }
}
