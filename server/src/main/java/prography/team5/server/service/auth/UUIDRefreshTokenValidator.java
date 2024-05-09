package prography.team5.server.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prography.team5.server.domain.auth.RefreshToken;
import prography.team5.server.domain.auth.RefreshTokenRepository;
import prography.team5.server.service.auth.RefreshTokenValidator;

@RequiredArgsConstructor
@Component
public class UUIDRefreshTokenValidator implements RefreshTokenValidator {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void validate(final String token) {
        final RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow();
        if(refreshToken.isExpired()) {
            refreshTokenRepository.deletedByToken(token);
            throw new IllegalArgumentException("리프레시 토큰 만료");
        }
    }
}
