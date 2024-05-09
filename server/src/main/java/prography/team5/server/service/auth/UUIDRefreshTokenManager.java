package prography.team5.server.service.auth;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import prography.team5.server.domain.auth.RefreshToken;
import prography.team5.server.domain.auth.RefreshTokenRepository;

@RequiredArgsConstructor
public class UUIDRefreshTokenManager implements RefreshTokenManager {

    private final RefreshTokenRepository refreshTokenRepository;
    private final long expirationDays;

    @Override
    public String provide(final long userId) {
        final LocalDateTime expiredAt = LocalDateTime.now().plusDays(expirationDays);
        final String token = UUID.randomUUID().toString();
        final RefreshToken refreshToken = new RefreshToken(userId, token, expiredAt);
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    @Override
    public void validate(final String token) {
        final RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow();
        if(refreshToken.isExpired()) {
            refreshTokenRepository.deleteByToken(token);
            throw new IllegalArgumentException("리프레시 토큰 만료");
        }
    }
}
