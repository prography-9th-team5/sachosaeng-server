package prography.team5.server.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import prography.team5.server.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailValue(final String email);

    boolean existsByEmailValue(String email);
}
