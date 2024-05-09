package prography.team5.server.service.auth;

public interface RefreshTokenManager {

    String provide(final long userId);

    void validate(final String token);

    long extractUserId(String token);
}
