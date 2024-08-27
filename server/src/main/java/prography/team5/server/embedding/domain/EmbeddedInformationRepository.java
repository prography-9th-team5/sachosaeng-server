package prography.team5.server.embedding.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import prography.team5.server.embedding.domain.EmbeddedInformation;

public interface EmbeddedInformationRepository extends JpaRepository<EmbeddedInformation, Long> {

    Optional<EmbeddedInformation> findTopByOrderByIdDesc();
}
