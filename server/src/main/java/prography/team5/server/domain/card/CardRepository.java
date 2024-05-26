package prography.team5.server.domain.card;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByCategoriesId(long categoryId);

    @Query("SELECT c FROM Card c WHERE c.id < :cursor ORDER BY c.id DESC")
    Slice<Card> findCardsBeforeCursor(@Param("cursor") long cursor, PageRequest pageRequest);

    @Query("SELECT c FROM Card c ORDER BY c.id DESC")
    Slice<Card> findLatestCards(PageRequest pageRequest);

}
