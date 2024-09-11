package prography.team5.server.version.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.common.EmptyData;
import prography.team5.server.version.AndroidVersionApiDocs;
import prography.team5.server.version.service.ForceUpdateRequest;
import prography.team5.server.version.service.VersionCheckResponse;
import prography.team5.server.version.service.VersionRequest;
import prography.team5.server.version.service.VersionService;
import prography.team5.server.version.service.VersionsWrapper;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/versions/android")
public class AndroidVersionController implements AndroidVersionApiDocs {

    private final VersionService versionService;

    @PostMapping
    public ResponseEntity<CommonApiResponse<EmptyData>> registerAndroidVersion(
            @RequestBody VersionRequest versionRequest
    ) {
        versionService.registerAndroidVersion(versionRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @GetMapping
    public ResponseEntity<CommonApiResponse<VersionsWrapper<List<VersionCheckResponse>>>> findAllAndroidVersions() {
        List<VersionCheckResponse> response = versionService.findAllAndroidVersions();
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new VersionsWrapper<>(response)));
    }

    @GetMapping("/{version}")
    public ResponseEntity<CommonApiResponse<VersionCheckResponse>> checkAndroidVersion(
            @PathVariable(value = "version") final String version
    ) {
        VersionCheckResponse response = versionService.checkAndroidVersion(version);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PutMapping("/force-update")
    public ResponseEntity<CommonApiResponse<EmptyData>> registerAndroidVersionForceUpdate(
            @RequestBody ForceUpdateRequest forceUpdateRequest
    ) {
        versionService.registerAndroidVersionForceUpdate(forceUpdateRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }
}
