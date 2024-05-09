package prography.team5.server.domain.auth;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import prography.team5.server.service.auth.RefreshTokenProvider;

@RequiredArgsConstructor
public class UUIDRefreshTokenProvider implements RefreshTokenProvider {

    private final RefreshTokenRepository refreshTokenRepository;
    private final long expirationDays;

    public String provide(final long userId) {
        final LocalDateTime expiredAt = LocalDateTime.now().plusDays(expirationDays);
        final String token = UUID.randomUUID().toString();
        final RefreshToken refreshToken = new RefreshToken(userId, token, expiredAt);
        refreshTokenRepository.save(refreshToken);
        return token;
    }
}
