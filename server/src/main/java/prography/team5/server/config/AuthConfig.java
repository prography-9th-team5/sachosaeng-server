package prography.team5.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prography.team5.server.infrastructure.JwtAccessTokenProvider;
import prography.team5.server.service.AccessTokenProvider;

@Configuration
public class AuthConfig {

    private final String secretKey;
    private final long accessTokenExpirationMinutes;

    public AuthConfig(
            @Value("${auth.jwt.secret-key}") final String secretKey,
            @Value("${auth.jwt.access-token-expiration-minutes}") final long accessTokenExpirationMinutes
    ) {
        this.secretKey = secretKey;
        this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
    }

    @Bean
    public AccessTokenProvider accessTokenProvider() {
        return new JwtAccessTokenProvider(secretKey, accessTokenExpirationMinutes);
    }
}
