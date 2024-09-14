package prography.team5.server.bookmark.domain;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prography.team5.server.card.domain.InformationCard;
import prography.team5.server.category.domain.Category;

public interface InformationCardBookmarkRepository extends JpaRepository<InformationCardBookmark, Long> {

    boolean existsByInformationCardAndUserId(InformationCard informationCard, Long userId);

    List<InformationCardBookmark> findAllByIdIn(List<Long> longs);

    List<InformationCardBookmark> findAllByUserId(Long userId, Sort id);

    List<InformationCardBookmark> findAllByUserId(Long userId);

    void deleteByUserIdAndInformationCardId(Long userId, Long informationCardId);

    // 최신순
    @Query("SELECT ib FROM InformationCardBookmark ib WHERE ib.userId = :userId AND ib.id < :lastCursor ORDER BY ib.id DESC")
    Slice<InformationCardBookmark> findBookmarksBeforeCursor(@Param("userId") Long userId,
                                                             @Param("lastCursor") Long lastCursor,
                                                             PageRequest pageRequest);

    @Query("SELECT ib FROM InformationCardBookmark ib WHERE ib.userId = :userId ORDER BY ib.id DESC")
    Slice<InformationCardBookmark> findLatestBookmarks(@Param("userId") Long userId, PageRequest pageRequest);

    @Query("SELECT ib FROM InformationCardBookmark ib " +
            "JOIN ib.informationCard ic " +
            "JOIN ic.categories c " +
            "WHERE ib.userId = :userId AND c = :category " +
            "ORDER BY ib.id DESC")
    Slice<InformationCardBookmark> findLatestBookmarksByUserIdAndCategory(
            @Param("userId") Long userId,
            @Param("category") Category category,
            PageRequest pageRequest
    );

    @Query("SELECT ib FROM InformationCardBookmark ib " +
            "JOIN ib.informationCard vc " +
            "JOIN vc.categories c " +
            "WHERE ib.userId = :userId AND c = :category AND ib.id < :lastCursor " +
            "ORDER BY ib.id DESC")
    Slice<InformationCardBookmark> findLatestBookmarksByUserIdAndCategoryBeforeCursor(@Param("userId") Long userId,
                                                                                      @Param("lastCursor") Long lastCursor,
                                                                                      @Param("category") Category category,
                                                                                      PageRequest pageRequest);
}
