package prography.team5.server.auth.service;

public interface RefreshTokenManager {

    String provide(final long userId);

    long extractUserId(String token);
}
