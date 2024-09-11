package prography.team5.server.embedding;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.common.InformationWrapper;
import prography.team5.server.embedding.service.dto.SimilarInformationResponse;

@Tag(name = "08. 연관 정보성 콘텐츠 조회", description = "특정 투표와 관련된 연관 정보 콘텐츠를 조회하하는 기능 관련입니다.")
public interface SimilarInformationApiDocs {

    @Operation(
            summary = "특정 투표와 연관된 정보성 콘텐츠 조회 API -> []를 {}로 감쌌어요!!",
            description = """
                        vote-id와 category-id를 주면 연관 콘텐츠 목록을 조회할 수 있습니다. \n
                        """
    )
    @ApiResponse(responseCode = "200", description = "인기 투표 목록 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<InformationWrapper<List<SimilarInformationResponse>>>> findSimilarInformation(
            @RequestParam(value = "category-id") final Long categoryId,
            @RequestParam(value = "vote-id") final Long voteId,
            @RequestParam(value = "size", defaultValue = "3") final int size
    );

}
