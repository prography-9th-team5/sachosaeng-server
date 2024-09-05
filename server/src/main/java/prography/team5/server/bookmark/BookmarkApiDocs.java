package prography.team5.server.bookmark;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import prography.team5.server.auth.controller.AuthRequired;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkResponse;
import prography.team5.server.card.service.dto.VoteResponse;
import prography.team5.server.common.CommonApiResponse;

@Tag(name = "10. 북마크", description = "북마크 관련 기능입니다.")
public interface BookmarkApiDocs {

    @Operation(
            summary = "[인증 토큰 필요] 투표에 북마크 추가 API",
            description = """
                    투표 북마크를 추가합니다. 요청에 voteId를 담아 보내면 북마크를 추가할 수 있습니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 추가를 성공한 경우 200을 반환합니다.")
    @ApiResponse(
            responseCode = "400",
            description = "voteId가 유효하지 않거나 이미 북마크가 등록되어 있는 경우 400을 반환합니다.",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                        {
                          "code": 0,
                          "message": "string",
                          "data": ""
                        }
                    """
                    )
            )
    )
    ResponseEntity<CommonApiResponse<Void>> createVoteCardBookmark(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody VoteCardBookmarkCreationRequest request
    );

    @Operation(
            summary = "[인증 토큰 필요] 투표 북마크 조회 API",
            description = """
                    투표 북마크를 조회합니다. default는 전체 카테고리 조회입니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 조회를 성공한 경우 200을 반환합니다.")
    ResponseEntity<CommonApiResponse<List<VoteCardBookmarkResponse>>> findVoteCardBookmark(
            @Parameter(hidden = true) Accessor accessor
    );

    @Operation(
            summary = "[인증 토큰 필요] 연관 콘텐츠에 북마크 추가 API",
            description = """
                    연관콘텐츠 북마크를 추가합니다. 요청에 informationId를 담아 보내면 북마크를 추가할 수 있습니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 추가를 성공한 경우 200을 반환합니다.")
    @ApiResponse(
            responseCode = "400",
            description = "informationId가 유효하지 않거나 이미 북마크가 등록되어 있는 경우 400을 반환합니다.",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                        {
                          "code": 0,
                          "message": "string",
                          "data": ""
                        }
                    """
                    )
            )
    )
    ResponseEntity<CommonApiResponse<Void>> createInformationCardBookmark(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody InformationCardBookmarkCreationRequest request
    );
}
