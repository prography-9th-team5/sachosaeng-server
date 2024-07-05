package prography.team5.server.card.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prography.team5.server.card.domain.UserVoteOption;

public interface UserVoteOptionRepository extends JpaRepository<UserVoteOption, Long> {

    boolean existsByUserIdAndVoteId(Long userId, long voteId);

    List<UserVoteOption> findByUserIdAndVoteId(Long userId, long voteId);

    @Query("SELECT uvo FROM UserVoteOption as uvo WHERE Date(uvo.createdAt) BETWEEN :startDate AND :endDate")
    List<UserVoteOption> findVotesByDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT uvo FROM UserVoteOption uvo " +
            "JOIN VoteCard vc ON uvo.voteId = vc.id " +
            "JOIN vc.categories c " +
            "WHERE c.id = :categoryId " +
            "AND DATE(uvo.createdAt) BETWEEN :startDate AND :endDate")
    List<UserVoteOption> findVotesByCategoryIdAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
