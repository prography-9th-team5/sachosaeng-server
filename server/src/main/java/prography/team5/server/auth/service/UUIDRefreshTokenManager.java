package prography.team5.server.auth.service;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prography.team5.server.auth.domain.RefreshToken;
import prography.team5.server.auth.domain.RefreshTokenRepository;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;

@RequiredArgsConstructor
public class UUIDRefreshTokenManager {

    private final RefreshTokenRepository refreshTokenRepository;
    private final long expirationDays;

    public String provide(final long userId, final String device) {
        final LocalDateTime expiredAt = LocalDateTime.now().plusDays(expirationDays);
        final String token = UUID.randomUUID().toString();
        final RefreshToken refreshToken = new RefreshToken(userId, token, expiredAt, device);
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public long extractUserId(final String token) {
        final RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_REFRESH_TOKEN));
        checkExpiration(refreshToken);
        return refreshToken.getUserId();
    }

    public void invalidateRefreshToken(final long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    private void checkExpiration(final RefreshToken refreshToken) {
        if (refreshToken.isExpired()) {
            refreshTokenRepository.deleteByToken(refreshToken.getToken());
            throw new SachosaengException(ErrorType.REFRESH_TOKEN_EXPIRATION);
        }
    }

    public void checkDevice(final String token, final String device) {
        final RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_REFRESH_TOKEN));
        refreshToken.checkDevice(device);
    }

    public String refresh(final long userId, final String oldToken, final String device) {
        final String newToken = provide(userId, device);
        refreshTokenRepository.deleteByToken(oldToken);
        return newToken;
    }
}
