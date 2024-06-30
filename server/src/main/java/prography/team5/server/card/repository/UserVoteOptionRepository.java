package prography.team5.server.card.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import prography.team5.server.card.domain.UserVoteOption;

public interface UserVoteOptionRepository extends JpaRepository<UserVoteOption, Long> {

    boolean existsByUserIdAndVoteId(Long userId, long voteId);

    List<UserVoteOption> findByUserIdAndVoteId(Long userId, long voteId);
}
