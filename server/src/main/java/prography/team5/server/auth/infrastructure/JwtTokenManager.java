package prography.team5.server.auth.infrastructure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import prography.team5.server.auth.service.AccessTokenManager;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;

public class JwtTokenManager implements AccessTokenManager {

    private static final String ACCESSOR_ID = "accessorId";
    private static final String CODE = "code";
    private static final String USER_CODE = "user";
    private static final int MINUTES_TO_MILLISECONDS = 60 * 1000;
    private final SecretKey secretKey;
    private final long expirationMinutes;
    private final SecretKey joinKey;

    public JwtTokenManager(final String secretKey, final long expirationMinutes, final String joinKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
        this.joinKey = Keys.hmacShaKeyFor(joinKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String provide(final long id) {
        Date now = new Date();
        return Jwts.builder()
                .claim(ACCESSOR_ID, id)
                .claim(CODE, USER_CODE) //todo: 지금은 사용하지 않고 추후 admin 구분시 사용
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMinutes * MINUTES_TO_MILLISECONDS))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String provideLoginToken(final long id) {
        Date now = new Date();
        return Jwts.builder()
                .claim(ACCESSOR_ID, id)
                .claim(CODE, USER_CODE)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMinutes * MINUTES_TO_MILLISECONDS))
                .signWith(joinKey)
                .compact();
    }

    @Override
    public Accessor extract(final String token) {
        try {
            final Claims payload = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            final Long accessorId = payload.get(ACCESSOR_ID, Long.class);
            return new Accessor(accessorId);
        } catch (MalformedJwtException e) {
            throw new SachosaengException(ErrorType.ACCESS_TOKEN_MALFORMED);
        } catch (UnsupportedJwtException e) {
            throw new SachosaengException(ErrorType.ACCESS_TOKEN_UNSUPPORTED);
        } catch (ExpiredJwtException e) {
            throw new SachosaengException(ErrorType.ACCESS_TOKEN_EXPIRATION);
        } catch (SignatureException e) {
            throw new SachosaengException(ErrorType.ACCESS_TOKEN_SIGNATURE_FAIL);
        }
        //todo: 예외 더 있으면 추가
    }

    @Override
    public Accessor extractFromLoginToken(final String token) {
        try {
            final Claims payload = Jwts.parser()
                    .verifyWith(joinKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            final Long accessorId = payload.get(ACCESSOR_ID, Long.class);
            return new Accessor(accessorId);
        } catch (MalformedJwtException e) {
            throw new SachosaengException(ErrorType.ACCESS_TOKEN_MALFORMED);
        } catch (UnsupportedJwtException e) {
            throw new SachosaengException(ErrorType.ACCESS_TOKEN_UNSUPPORTED);
        } catch (ExpiredJwtException e) {
            throw new SachosaengException(ErrorType.ACCESS_TOKEN_EXPIRATION);
        } catch (SignatureException e) {
            throw new SachosaengException(ErrorType.ACCESS_TOKEN_SIGNATURE_FAIL);
        }
        //todo: 예외 더 있으면 추가
    }
}
