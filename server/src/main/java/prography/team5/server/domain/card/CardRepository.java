package prography.team5.server.domain.card;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("SELECT c FROM Card c WHERE c.id < :cursor ORDER BY c.id DESC")
    Slice<Card> findBeforeCursor(@Param("cursor") long cursor, PageRequest pageRequest);

    @Query("SELECT c FROM Card c ORDER BY c.id DESC")
    Slice<Card> findLatestCards(PageRequest pageRequest);

    @Query("SELECT c FROM Card c JOIN c.categories cat " +
            "WHERE cat.id = :categoryId AND c.id < :cursor ORDER BY c.id DESC")
    Slice<Card> findByCategoriesIdBeforeCursor(@Param("cursor") long cursor, @Param("categoryId") long categoryId, PageRequest pageRequest);

    @Query("SELECT c FROM Card c JOIN c.categories cat " +
            "WHERE cat.id = :categoryId ORDER BY c.id DESC")
    Slice<Card> findLatestCardsByCategoriesId(@Param("categoryId") long categoryId, PageRequest pageRequest);
}
