package prography.team5.server.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prography.team5.server.auth.domain.RefreshTokenRepository;
import prography.team5.server.auth.infrastructure.JwtTokenManager;
import prography.team5.server.auth.service.AccessTokenManager;
import prography.team5.server.auth.service.RefreshTokenManager;
import prography.team5.server.auth.service.UUIDRefreshTokenManager;

@Configuration
public class AuthConfig {

    private final String secretKey;
    private final long accessTokenExpirationMinutes;
    private final long refreshTokenExpirationDays;

    public AuthConfig(
            @Value("${auth.jwt.secret-key}") final String secretKey,
            @Value("${auth.jwt.access-token-expiration-minutes}") final long accessTokenExpirationMinutes,
            @Value("${auth.refresh-token.expiration-days}") final long refreshTokenExpirationDays
    ) {
        this.secretKey = secretKey;
        this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
        this.refreshTokenExpirationDays = refreshTokenExpirationDays;
    }

    @Bean
    public AccessTokenManager accessTokenManager() {
        return new JwtTokenManager(secretKey, accessTokenExpirationMinutes);
    }

    @Bean
    public RefreshTokenManager refreshTokenManager(@Autowired RefreshTokenRepository refreshTokenRepository) {
        return new UUIDRefreshTokenManager(refreshTokenRepository, refreshTokenExpirationDays);
    }
}
