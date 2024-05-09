package prography.team5.server.infrastructure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import prography.team5.server.service.auth.AccessTokenManager;
import prography.team5.server.service.auth.dto.VerifiedUser;

public class JwtTokenManager implements AccessTokenManager {

    private static final String USER_ID = "userId";
    private static final int MINUTES_TO_MILLISECONDS = 60 * 1000;

    private final SecretKey secretKey;
    private final long expirationMinutes;

    public JwtTokenManager(final String secretKey, final long expirationMinutes) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    @Override
    public String provide(final long userId) {
        Date now = new Date();
        return Jwts.builder()
                .claim(USER_ID, userId)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMinutes * MINUTES_TO_MILLISECONDS))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public VerifiedUser extract(final String token) {
        try {
            final Claims payload = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            final Long userId = payload.get(USER_ID, Long.class);
            return new VerifiedUser(userId);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException();
        } catch (SignatureException e) {
            throw new IllegalArgumentException();
        }
    }
}
