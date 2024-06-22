package prography.team5.server.user.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.team5.server.common.domian.TimeRecord;
import prography.team5.server.util.RandomNicknameGenerator;

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
    private String nickname;

    private boolean deleted = false;

    @Getter
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    public User(final String email) {
        this.email = Email.from(email);
        this.nickname = RandomNicknameGenerator.generate();
        this.userType = UserType.UNDEFINED;
    }

    public String getEmail() {
        return email.getValue();
    }

    public void updateNickname(final String nickname) {
        this.nickname = nickname;
    }

    public void updateUserType(final String userType) {
        this.userType = UserType.convert(userType);
    }
}
