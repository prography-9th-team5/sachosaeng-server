package prography.team5.server.bookmark.domain;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import prography.team5.server.card.domain.InformationCard;

public interface InformationCardBookmarkRepository extends JpaRepository<InformationCardBookmark, Long> {

    boolean existsByInformationCardAndUserId(InformationCard informationCard, Long userId);

    List<InformationCardBookmark> findAllByIdIn(List<Long> longs);

    List<InformationCardBookmark> findAllByUserId(Long userId, Sort id);
}
