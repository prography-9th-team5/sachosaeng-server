package prography.team5.server.domain.user;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;
import prography.team5.server.user.domain.Email;

class EmailTest {

    @Test
    void 이메일_null_검증() {
        assertThatThrownBy(() -> Email.from(null))
                .isInstanceOf(SachosaengException.class)
                .hasMessage(ErrorType.INVALID_EMAIL_FORMAT.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "string", "string@", "@", "@string"})
    void 이메일_유효성_검증_실패(final String email) {
        assertThatThrownBy(() -> Email.from(email))
                .isInstanceOf(SachosaengException.class)
                .hasMessage(ErrorType.INVALID_EMAIL_FORMAT.getMessage());
    }

    @Test
    void 이메일_유효성_검증_성공() {
        assertThatCode(() -> Email.from("user@email.com")).doesNotThrowAnyException();
    }
}
