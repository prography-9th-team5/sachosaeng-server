package prography.team5.server.card.repository;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prography.team5.server.card.domain.VoteCard;

public interface VoteCardRepository extends JpaRepository<VoteCard, Long> {

    @Query("SELECT c FROM VoteCard c ORDER BY c.id DESC")
    Slice<VoteCard> findLatestCards(PageRequest pageRequest);

    @Query("SELECT c FROM VoteCard c WHERE c.id < :cursor ORDER BY c.id DESC")
    Slice<VoteCard> findBeforeCursor(@Param("cursor") Long cursor, PageRequest pageRequest);

    @Query("SELECT c FROM VoteCard c JOIN c.categories cat " +
            "WHERE cat.id = :categoryId ORDER BY c.id DESC")
    Slice<VoteCard> findLatestCardsByCategoriesId(@Param("categoryId") long categoryId, PageRequest pageRequest);

    @Query("SELECT c FROM VoteCard c JOIN c.categories cat " +
            "WHERE cat.id = :categoryId AND c.id < :cursor ORDER BY c.id DESC")
    Slice<VoteCard> findByCategoriesIdBeforeCursor(
            @Param("cursor") Long cursor,
            @Param("categoryId") long categoryId,
            PageRequest pageRequest
    );

    @Query("SELECT c FROM VoteCard c ORDER BY c.participantCount ASC, c.id ASC")
    List<VoteCard> findWithFewestParticipants(PageRequest pageRequest);

    @Query("SELECT MIN(c.participantCount) FROM VoteCard c")
    Integer findMinParticipantCount();

    @Query("SELECT c FROM VoteCard c WHERE c.participantCount = :minCount ORDER BY c.id ASC")
    List<VoteCard> findWithFewestParticipants(@Param("minCount") Integer minCount);
}
