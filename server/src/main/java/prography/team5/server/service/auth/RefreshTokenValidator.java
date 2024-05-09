package prography.team5.server.service.auth;

public interface RefreshTokenValidator {

    void validate(final String token);
}
