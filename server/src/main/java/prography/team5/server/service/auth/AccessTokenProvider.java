package prography.team5.server.service.auth;

public interface AccessTokenProvider {

    String provide(final long id);
}
