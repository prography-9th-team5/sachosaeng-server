package prography.team5.server.version.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, Long> {

    boolean existsByNameAndPlatform(String version, Platform platform);
}
