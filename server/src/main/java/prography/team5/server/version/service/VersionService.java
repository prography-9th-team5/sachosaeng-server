package prography.team5.server.version.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;
import prography.team5.server.version.domain.Platform;
import prography.team5.server.version.domain.Version;
import prography.team5.server.version.domain.VersionRepository;

@RequiredArgsConstructor
@Service
public class VersionService {

    private final VersionRepository versionRepository;

    @Transactional
    public void registerIosVersion(final VersionRequest request) {
        if(versionRepository.existsByNameAndPlatform(request.version(), Platform.IOS)) {
            throw new SachosaengException(ErrorType.DUPLICATED_VERSION);
        }

        final Version version = new Version(request.version(), Platform.IOS);
        versionRepository.save(version);
    }
}
