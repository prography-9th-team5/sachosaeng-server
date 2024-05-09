package prography.team5.server.service.auth;

public interface RefreshTokenProvider {

    String provide(final long userId);
}
