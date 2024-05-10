package prography.team5.server.service.auth;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import prography.team5.server.domain.auth.RefreshToken;
import prography.team5.server.domain.auth.RefreshTokenRepository;
import prography.team5.server.exception.ErrorType;
import prography.team5.server.exception.SachosaengException;

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
        final RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_REFRESH_TOKEN));
        if (refreshToken.isExpired()) {
            refreshTokenRepository.deleteByToken(token);
            throw new SachosaengException(ErrorType.REFRESH_TOKEN_EXPIRATION);
        }
    }

    @Override
    public long extractUserId(final String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_REFRESH_TOKEN))
                .getUserId();
    }
}
