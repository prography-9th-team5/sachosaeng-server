package prography.team5.server.version;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.common.EmptyData;
import prography.team5.server.version.service.ForceUpdateRequest;
import prography.team5.server.version.service.VersionCheckResponse;
import prography.team5.server.version.service.VersionRequest;
import prography.team5.server.version.service.VersionsWrapper;

@Tag(name = "00-2. Android 버전", description = "Android 버전 관련 기능입니다.")
public interface AndroidVersionApiDocs {

    @Operation(
            summary = "[Android] 최신 버전 등록 API",
            description = "최신 버전을 등록할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "최신 버전 등록 성공입니다.")
    ResponseEntity<CommonApiResponse<EmptyData>> registerAndroidVersion(
            @RequestBody VersionRequest versionRequest
    );

    @Operation(
            summary = "[Android] 모든 버전 조회 API -> []를 {}로 감쌌어요!!",
            description = "모든 버전을 조회할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "모든 버전 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<VersionsWrapper<List<VersionCheckResponse>>>> findAllAndroidVersions();

    @Operation(
            summary = "[Android] 버전 체크 API",
            description = "체크할 버전을 path variable로 담아 보내면 해당 버전이 최신 버전인지, 강제업데이트가 필요한지를 확인할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "버전 체크 성공입니다.")
    public ResponseEntity<CommonApiResponse<VersionCheckResponse>> checkAndroidVersion(
            @PathVariable(value = "version") final String version
    );

    @Operation(
            summary = "[Android] 강제 업데이트 등록/해제 API",
            description = "특정 버전들의 강제 업데이트를 등록하거나 해제할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "강제 업데이트 등록 성공입니다.")
    ResponseEntity<CommonApiResponse<EmptyData>> registerAndroidVersionForceUpdate(
            @RequestBody ForceUpdateRequest forceUpdateRequest
    );
}
