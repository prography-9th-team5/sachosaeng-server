package prography.team5.server.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.team5.server.auth.service.EmailEncryptor;
import prography.team5.server.common.domian.TimeRecord;

@Getter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Withdraw extends TimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String encryptedEmail;
    private String reason;

    public Withdraw(final Long userId, final String encryptedEmail, final String reason) {
        this.userId = userId;
        this.encryptedEmail = encryptedEmail;
        this.reason = reason;
    }

    public static Withdraw of(final Long userId, final String email, final String reason) {
        return new Withdraw(
                userId,
                EmailEncryptor.encrypt(email),
                reason
        );
    }
}
