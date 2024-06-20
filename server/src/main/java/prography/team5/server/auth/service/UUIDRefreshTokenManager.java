package prography.team5.server.auth.service;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import prography.team5.server.auth.domain.RefreshToken;
import prography.team5.server.auth.domain.RefreshTokenRepository;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;

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
    public long extractUserId(final String token) {
        final RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_REFRESH_TOKEN));
        checkExpiration(refreshToken);
        return refreshToken.getUserId();
    }

    private void checkExpiration(final RefreshToken refreshToken) {
        if (refreshToken.isExpired()) {
            refreshTokenRepository.deleteByToken(refreshToken.getToken());
            throw new SachosaengException(ErrorType.REFRESH_TOKEN_EXPIRATION);
        }
    }
}
