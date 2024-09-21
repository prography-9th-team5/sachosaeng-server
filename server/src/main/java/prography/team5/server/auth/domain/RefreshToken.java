package prography.team5.server.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "member_id")
    private Long userId;
    private String token;
    private LocalDateTime expiredAt;
    private String device;

    public RefreshToken(final Long userId, final String token, final LocalDateTime expiredAt) {
        this.userId = userId;
        this.token = token;
        this.expiredAt = expiredAt;
    }

    public RefreshToken(final Long userId, final String token, final LocalDateTime expiredAt, final String device) {
        this.userId = userId;
        this.token = token;
        this.expiredAt = expiredAt;
        this.device = device;
    }

    public boolean isExpired() {
        return expiredAt.isBefore(LocalDateTime.now());
    }

    public void checkDevice(final String device) {
        if(Strings.isEmpty(this.device)) {
            return;
        }
        if(!Objects.equals(this.device, device)) {
            throw new SachosaengException(ErrorType.DEVICE_NOT_MATCH);
        }
    }
}
