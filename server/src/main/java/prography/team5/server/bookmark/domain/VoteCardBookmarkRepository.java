package prography.team5.server.bookmark.domain;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import prography.team5.server.card.domain.VoteCard;

public interface VoteCardBookmarkRepository extends JpaRepository<VoteCardBookmark, Long> {

    boolean existsByVoteCardAndUserId(VoteCard voteCard, Long userId);

    List<VoteCardBookmark> findAllByUserId(Long userId, Sort sort);

    List<VoteCardBookmark> findAllByUserId(Long userId);

    List<VoteCardBookmark> findAllByIdIn(List<Long> ids);

    void deleteByUserIdAndVoteCardId(Long userId, Long voteCardId);
}
