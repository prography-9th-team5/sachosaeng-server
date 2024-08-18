package prography.team5.server.card.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prography.team5.server.card.domain.SuggestionVoteCard;

public interface SuggestionVoteCardRepository extends JpaRepository<SuggestionVoteCard, Long> {

    @Query("SELECT s FROM SuggestionVoteCard s WHERE s.viewDate >= :startDate")
    List<SuggestionVoteCard> findRecentSuggestionVoteCards(@Param("startDate") LocalDate startDate);
}
