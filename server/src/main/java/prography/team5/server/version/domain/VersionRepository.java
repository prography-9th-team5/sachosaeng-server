package prography.team5.server.version.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, Long> {

    boolean existsByNameAndPlatform(String version, Platform platform);

    List<Version> findAllByPlatform(Platform platform);

    Optional<Version> findByNameAndPlatform(String name, Platform platform);

    List<Version> findAllByPlatformAndNameIn(Platform platform, List<String> names);
}
