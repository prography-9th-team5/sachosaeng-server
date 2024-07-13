package prography.team5.server.embedding;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmbeddedInformationRepository extends JpaRepository<EmbeddedInformation, Long> {

    Optional<EmbeddedInformation> findTopByOrderByIdDesc();
}
