package prography.team5.server.auth.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import prography.team5.server.auth.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(final String token);

    void deleteByToken(final String token);

    void deleteByUserId(long userId);
}
