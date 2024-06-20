package prography.team5.server.auth.service;

import io.jsonwebtoken.lang.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.user.domain.User;
import prography.team5.server.user.domain.UserRepository;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;
import prography.team5.server.auth.service.dto.AccessTokenResponse;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.auth.service.dto.EmailRequest;
import prography.team5.server.auth.service.dto.LoginResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager refreshTokenManager;

    @Transactional
    public long joinNewUser(final EmailRequest emailRequest) {
        if (userRepository.existsByEmailValue(emailRequest.email())) {
            throw new SachosaengException(ErrorType.DUPLICATED_EMAIL);
        }
        final User user = new User(emailRequest.email());
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public LoginResponse login(final EmailRequest emailRequest) {
        final User user = userRepository.findByEmailValue(emailRequest.email())
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_EMAIL));
        final String accessToken = accessTokenManager.provide(user.getId());
        final String refreshToken = refreshTokenManager.provide(user.getId());
        return new LoginResponse(user.getId(), accessToken, refreshToken);
    }

    public Accessor verifyUserFromToken(final String token) {
        return accessTokenManager.extract(token);
    }

    @Transactional
    public AccessTokenResponse refreshAccessToken(final String refreshToken) {
        if (Objects.isEmpty(refreshToken)) {
            throw new SachosaengException(ErrorType.NO_REFRESH_TOKEN);
        }
        final long userId = refreshTokenManager.extractUserId(refreshToken);
        final String accessToken = accessTokenManager.provide(userId);
        return new AccessTokenResponse(accessToken);
    }
}
