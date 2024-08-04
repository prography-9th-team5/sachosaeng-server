package prography.team5.server.version.service;

import java.util.List;
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

    @Transactional(readOnly = true)
    public List<VersionCheckResponse> findAllIosVersions() {
        final List<Version> allByPlatform = versionRepository.findAllByPlatform(Platform.IOS);
        final Version latestVersion = allByPlatform.get(allByPlatform.size() - 1);
        return allByPlatform.stream()
                .map(each -> new VersionCheckResponse(
                        each.getName(),
                        each.getPlatform().name(),
                        each.equals(latestVersion),
                        each.isForceUpdateRequired()
                )).toList();
    }

    @Transactional(readOnly = true)
    public VersionCheckResponse checkIosVersion(final String versionName) {
        final List<Version> allByPlatform = versionRepository.findAllByPlatform(Platform.IOS);
        if(allByPlatform.isEmpty()) {
            throw new SachosaengException(ErrorType.NO_VERSION);
        }
        final Version latestVersion = allByPlatform.get(allByPlatform.size() - 1);
        final Version version = allByPlatform.stream()
                .filter(each -> each.getName().equals(versionName))
                .findFirst()
                .orElseThrow(() -> new SachosaengException(ErrorType.NO_SPECIFIC_VERSION));

        return new VersionCheckResponse(version.getName(), version.getPlatform().name(), version.equals(latestVersion),version.isForceUpdateRequired());
    }

    @Transactional
    public void registerIosVersionForceUpdate(final ForceUpdateRequest request) {
        final List<Version> versions = versionRepository.findAllByPlatformAndNameIn(Platform.IOS,
                request.versions());
        versions.forEach(each -> each.forceUpdate(request.forceUpdateRequired()));
    }
}
