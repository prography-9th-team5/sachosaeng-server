package prography.team5.server.service.auth;

import io.jsonwebtoken.lang.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.User;
import prography.team5.server.domain.UserRepository;
import prography.team5.server.service.auth.dto.AccessTokenResponse;
import prography.team5.server.service.auth.dto.EmailRequest;
import prography.team5.server.service.auth.dto.LoginResponse;
import prography.team5.server.service.auth.dto.VerifiedUser;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager refreshTokenManager;

    //todo: 전체적으로 예외처리
    public long joinNewUser(final EmailRequest emailRequest) {
        final User user = new User(emailRequest.email());
        userRepository.save(user);
        return user.getId();
    }

    public LoginResponse login(final EmailRequest emailRequest) {
        final User user = userRepository.findByEmail(emailRequest.email())
                .orElseThrow();
        final String accessToken = accessTokenManager.provide(user.getId());
        final String refreshToken = refreshTokenManager.provide(user.getId());
        return new LoginResponse(user.getId(), accessToken, refreshToken);
    }

    public VerifiedUser verifyUserFromToken(final String token) {
        return accessTokenManager.extract(token);
    }

    public AccessTokenResponse refreshAccessToken(final String refreshToken) {
        if(Objects.isEmpty(refreshToken)) {
            throw new IllegalArgumentException("리프레시 토큰 없음");
        }
        refreshTokenManager.validate(refreshToken);
        final long userId = refreshTokenManager.extractUserId(refreshToken);
        final String accessToken = accessTokenManager.provide(userId);
        return new AccessTokenResponse(accessToken);
    }
}
