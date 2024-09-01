package prography.team5.server.auth.service;

public interface RefreshTokenManager {

    String provide(final long userId);

    long extractUserId(String token);

    void invalidateRefreshToken(final long userId);

    void extend(final String token);
}
