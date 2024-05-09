package prography.team5.server.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.User;
import prography.team5.server.domain.UserRepository;
import prography.team5.server.service.auth.dto.EmailRequest;
import prography.team5.server.service.auth.dto.LoginResponse;
import prography.team5.server.service.auth.dto.VerifiedUser;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AccessTokenProvider accessTokenProvider;
    private final AccessTokenExtractor accessTokenExtractor;
    private final RefreshTokenProvider refreshTokenProvider;

    public long joinNewUser(final EmailRequest emailRequest) {
        final User user = new User(emailRequest.email());
        userRepository.save(user);
        return user.getId();
    }

    public LoginResponse login(final EmailRequest emailRequest) {
        final User user = userRepository.findByEmail(emailRequest.email())
                .orElseThrow();
        final String accessToken = accessTokenProvider.provide(user.getId());
        final String refreshToken = refreshTokenProvider.provide(user.getId());
        return new LoginResponse(user.getId(), accessToken, refreshToken);
    }

    public VerifiedUser verifyUserFromToken(final String token) {
        return accessTokenExtractor.extract(token);
    }
}
