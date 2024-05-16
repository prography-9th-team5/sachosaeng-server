package prography.team5.server.service.auth;

public interface RefreshTokenManager {

    String provide(final long userId);

    long extractUserId(String token);
}
