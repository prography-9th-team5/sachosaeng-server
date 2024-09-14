package prography.team5.server.bookmark.domain;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.Category;

public interface VoteCardBookmarkRepository extends JpaRepository<VoteCardBookmark, Long> {

    boolean existsByVoteCardAndUserId(VoteCard voteCard, Long userId);

    List<VoteCardBookmark> findAllByUserId(Long userId, Sort sort);

    List<VoteCardBookmark> findAllByUserId(Long userId);

    List<VoteCardBookmark> findAllByIdIn(List<Long> ids);

    void deleteByUserIdAndVoteCardId(Long userId, Long voteCardId);

    // 최신순
    @Query("SELECT v FROM VoteCardBookmark v WHERE v.userId = :userId AND v.id < :lastCursor ORDER BY v.id DESC")
    Slice<VoteCardBookmark> findBookmarksBeforeCursor(@Param("userId") Long userId,
                                                      @Param("lastCursor") Long lastCursor, PageRequest pageRequest);

    @Query("SELECT v FROM VoteCardBookmark v WHERE v.userId = :userId ORDER BY v.id DESC")
    Slice<VoteCardBookmark> findLatestBookmarks(@Param("userId") Long userId, PageRequest pageRequest);

    @Query("SELECT vcb FROM VoteCardBookmark vcb " +
            "JOIN vcb.voteCard vc " +
            "JOIN vc.categories c " +
            "WHERE vcb.userId = :userId AND c = :category " +
            "ORDER BY vcb.id DESC")
    Slice<VoteCardBookmark> findLatestBookmarksByUserIdAndCategory(
            @Param("userId") Long userId,
            @Param("category") Category category,
            PageRequest pageRequest
    );

    @Query("SELECT vcb FROM VoteCardBookmark vcb " +
            "JOIN vcb.voteCard vc " +
            "JOIN vc.categories c " +
            "WHERE vcb.userId = :userId AND c = :category AND vcb.id < :lastCursor " +
            "ORDER BY vcb.id DESC")
    Slice<VoteCardBookmark> findLatestBookmarksByUserIdAndCategoryBeforeCursor(@Param("userId") Long userId,
                                                                               @Param("category") Category category,
                                                                               @Param("lastCursor") Long lastCursor,
                                                                               PageRequest pageRequest);

    // 오래된순
    @Query("SELECT v FROM VoteCardBookmark v WHERE v.userId = :userId AND v.id > :lastCursor ORDER BY v.id ASC")
    Slice<VoteCardBookmark> findBookmarksAfterCursor(@Param("userId") Long userId, @Param("lastCursor") Long lastCursor,
                                                     PageRequest pageRequest);
}
