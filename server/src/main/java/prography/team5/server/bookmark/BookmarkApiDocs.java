package prography.team5.server.bookmark;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import prography.team5.server.auth.controller.AuthRequired;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkCreationRequest;
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
    @ApiResponse(responseCode = "200", description = "북마크 추가 성공입니다.")
    ResponseEntity<CommonApiResponse<VoteResponse>> createVoteCardBookmark(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody VoteCardBookmarkCreationRequest request
    );

    @Operation(
            summary = "[인증 토큰 필요] 연관 콘텐츠에 북마크 추가 API",
            description = """
                    연관콘텐츠 북마크를 추가합니다. 요청에 informationId를 담아 보내면 북마크를 추가할 수 있습니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 추가 성공입니다.")
    ResponseEntity<CommonApiResponse<VoteResponse>> createInformationCardBookmark(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody InformationCardBookmarkCreationRequest request
    );
}
