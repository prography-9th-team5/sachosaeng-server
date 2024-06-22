package prography.team5.server.card.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVoteOptionRepository extends JpaRepository<UserVoteOption, Long> {

    boolean existsByUserIdAndVoteId(Long userId, long voteId);

    Optional<UserVoteOption> findByUserIdAndVoteId(Long userId, long voteId);
}
