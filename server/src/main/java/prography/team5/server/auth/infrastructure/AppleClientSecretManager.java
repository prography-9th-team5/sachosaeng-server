package prography.team5.server.auth.infrastructure;

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.Objects;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppleClientSecretManager {

    private final String kid;
    private final String issuer;
    private final String audience;
    private final String subject;
    private final String key;

    public AppleClientSecretManager(
            @Value("${auth.apple.kid}") final String kid,
            @Value("${auth.apple.issuer}") final String issuer,
            @Value("${auth.apple.aud}") final String audience,
            @Value("${auth.apple.sub}") final String subject,
            @Value("${auth.apple.key}") final String key

    ) {
        this.kid = kid;
        this.issuer = issuer;
        this.audience = audience;
        this.subject = subject;
        this.key = key;
    }

    public String provideToken() {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000*12); // 12시간

        return Jwts.builder()
                .header()
                .add("alg", "ES256")
                .add("kid", kid)
                .and()
                .issuer(issuer)
                .issuedAt(now)
                .expiration(expiration)
                .claim("aud", audience)
                .subject(subject)
                .signWith(loadPrivateKey())
                .compact();
    }

    private PrivateKey loadPrivateKey() {
        try {
            PemReader pemReader = new PemReader(new InputStreamReader(
                    Objects.requireNonNull(getClass().getResourceAsStream(key)),
                    StandardCharsets.UTF_8
            ));
            byte[] content = pemReader.readPemObject()
                    .getContent();

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(content);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");

            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
