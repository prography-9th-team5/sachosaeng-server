package prography.team5.server.card.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prography.team5.server.card.domain.DailyVoteCard;

public interface DailyVoteCardRepository extends JpaRepository<DailyVoteCard, Long> {

    @EntityGraph(attributePaths = {"voteCard"})
    @Query("SELECT dvc FROM DailyVoteCard dvc WHERE dvc.viewDate = :date")
    Optional<DailyVoteCard> findByDate(@Param("date") LocalDate date);
}
