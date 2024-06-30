package prography.team5.server.card.domain;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DailyVoteCardRepository extends JpaRepository<DailyVoteCard, Long> {

    @EntityGraph(attributePaths = {"voteCard"})
    @Query("SELECT dvc FROM DailyVoteCard dvc WHERE DATE(dvc.createdAt) = :date")
    Optional<DailyVoteCard> findByDate(@Param("date") LocalDate date);
}
