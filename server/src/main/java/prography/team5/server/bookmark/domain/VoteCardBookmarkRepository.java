package prography.team5.server.bookmark.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import prography.team5.server.card.domain.VoteCard;

public interface VoteCardBookmarkRepository extends JpaRepository<VoteCardBookmark, Long> {

    boolean existsByVoteCardAndUserId(VoteCard voteCard, Long userId);
}
