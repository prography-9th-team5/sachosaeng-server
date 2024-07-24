package prography.team5.server.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import prography.team5.server.auth.service.EmailEncryptor;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @Column(name = "email", nullable = false, unique = true)
    private String value;

    private Email(final String value) {
        this.value = value;
    }

    public static Email from(final String email) {
        validate(email);
        return new Email(email);
    }

    private static void validate(final String email) {
        if (Strings.isBlank(email) || !Pattern.matches(EMAIL_PATTERN, email)) {
            throw new SachosaengException(ErrorType.INVALID_EMAIL_FORMAT);
        }
    }

    public void encrypt() {
        this.value = EmailEncryptor.encrypt(this.value);
    }
}
