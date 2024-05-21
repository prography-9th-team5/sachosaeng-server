package prography.team5.server.domain.user;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.team5.server.domain.TimeRecord;

@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class User extends TimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Embedded
    private Email email;
    @Getter
    private String nickname = "멋쟁이 프로도"; //todo: 닉네임 랜덤 생성기
    private boolean deleted = false;

    public User(final String email) {
        this.email = Email.from(email);
    }

    public String getEmail() {
        return email.getValue();
    }

    public void updateNickname(final String nickname) {
        this.nickname = nickname;
    }
}
