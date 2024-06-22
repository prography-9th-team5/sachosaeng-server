package prography.team5.server.card.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVoteOptionRepository extends JpaRepository<UserVoteOption, Long> {

    boolean existsByUserIdAndVoteId(Long userId, long voteId);
}
