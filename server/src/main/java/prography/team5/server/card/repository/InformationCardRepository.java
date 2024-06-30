package prography.team5.server.card.repository;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prography.team5.server.card.domain.InformationCard;

public interface InformationCardRepository extends JpaRepository<InformationCard, Long> {

    @Query("SELECT c FROM InformationCard c WHERE c.id < :cursor ORDER BY c.id DESC")
    Slice<InformationCard> findBeforeCursor(@Param("cursor") long cursor, PageRequest pageRequest);

    @Query("SELECT c FROM InformationCard c ORDER BY c.id DESC")
    Slice<InformationCard> findLatestCards(PageRequest pageRequest);

    @Query("SELECT c FROM InformationCard c JOIN c.categories cat " +
            "WHERE cat.id = :categoryId AND c.id < :cursor ORDER BY c.id DESC")
    Slice<InformationCard> findByCategoriesIdBeforeCursor(@Param("cursor") long cursor,
                                                          @Param("categoryId") long categoryId,
                                                          PageRequest pageRequest);

    @Query("SELECT c FROM InformationCard c JOIN c.categories cat " +
            "WHERE cat.id = :categoryId ORDER BY c.id DESC")
    Slice<InformationCard> findLatestCardsByCategoriesId(@Param("categoryId") long categoryId, PageRequest pageRequest);

    @Query("SELECT c FROM InformationCard c JOIN c.categories cat " +
            "WHERE cat.id IN :categoryIds ORDER BY c.id DESC")
    Slice<InformationCard> findLatestCardsByCategoriesIdIn(@Param("categoryIds") List<Long> categoryIds,
                                                           PageRequest pageRequest);

    @Query("SELECT c FROM InformationCard c JOIN c.categories cat " +
            "WHERE cat.id IN :categoryIds AND c.id < :cursor ORDER BY c.id DESC")
    Slice<InformationCard> findByCategoriesIdInBeforeCursor(
            @Param("cursor") long cursor,
            @Param("categoryIds") List<Long> categoryIds,
            PageRequest pageRequest
    );
}
