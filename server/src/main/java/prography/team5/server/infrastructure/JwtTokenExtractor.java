package prography.team5.server.infrastructure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import prography.team5.server.service.auth.AccessTokenExtractor;
import prography.team5.server.service.auth.dto.VerifiedUser;

public class JwtTokenExtractor implements AccessTokenExtractor {

    private static final String USER_ID = "userId";

    private final SecretKey secretKey;

    public JwtTokenExtractor(final String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
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
