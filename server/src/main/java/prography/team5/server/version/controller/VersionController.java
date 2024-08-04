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
import prography.team5.server.version.VersionApiDocs;
import prography.team5.server.version.service.ForceUpdateRequest;
import prography.team5.server.version.service.VersionCheckResponse;
import prography.team5.server.version.service.VersionRequest;
import prography.team5.server.version.service.VersionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/versions")
public class VersionController implements VersionApiDocs {

    private final VersionService versionService;

    // 최신 버전 등록
    @PostMapping("/ios")
    public ResponseEntity<CommonApiResponse<Void>> registerIosVersion(
            @RequestBody VersionRequest versionRequest
    ) {
        versionService.registerIosVersion(versionRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }

    // 모든 버전 조회
    @GetMapping("/ios")
    public ResponseEntity<CommonApiResponse<List<VersionCheckResponse>>> findAllIosVersions() {
        List<VersionCheckResponse> response = versionService.findAllIosVersions();
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    // 체크
    @GetMapping("/ios/{version}")
    public ResponseEntity<CommonApiResponse<VersionCheckResponse>> checkIosVersion(
            @PathVariable(value = "version") final String version
    ) {
        VersionCheckResponse response = versionService.checkIosVersion(version);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    // 강제 업데이트 등록/해제
    @PutMapping("/ios/force-update")
    public ResponseEntity<CommonApiResponse<Void>> registerIosVersionForceUpdate(
            @RequestBody ForceUpdateRequest forceUpdateRequest
    ) {
        versionService.registerIosVersionForceUpdate(forceUpdateRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
