package prography.team5.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prography.team5.server.infrastructure.JwtTokenExtractor;
import prography.team5.server.infrastructure.JwtTokenProvider;
import prography.team5.server.service.auth.AccessTokenExtractor;
import prography.team5.server.service.auth.AccessTokenProvider;

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
        return new JwtTokenProvider(secretKey, accessTokenExpirationMinutes);
    }

    @Bean
    public AccessTokenExtractor accessTokenExtractor() {
        return new JwtTokenExtractor(secretKey);
    }
}
