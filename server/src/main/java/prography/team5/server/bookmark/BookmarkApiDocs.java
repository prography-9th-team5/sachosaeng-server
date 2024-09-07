package prography.team5.server.bookmark;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkDeletionRequest;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkResponse;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkDeletionRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkResponse;
import prography.team5.server.category.service.dto.CategoryResponse;
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
            summary = "[인증 토큰 필요] 투표 북마크 제거 API",
            description = """
                    제거할 북마크들의 voteBookmarkId들을 담아 보내면 투표 북마크를 제거합니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 제거에 성공한 경우 200을 반환합니다.")
    @ApiResponse(
            responseCode = "400",
            description = "요청에 포함된 voteBookmarkId에 다른 유저의 북마크가 포함되어있는경우 400을 반환합니다.",
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
    ResponseEntity<CommonApiResponse<Void>> deleteVoteCardBookmarks(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody VoteCardBookmarkDeletionRequest request
    );

    @Operation(
            summary = "[인증 토큰 필요] ALL에 대한 투표 북마크 조회 API",
            description = """
                    투표 북마크를 조회합니다. 전체 카테고리에 대한 조회입니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 조회를 성공한 경우 200을 반환합니다.")
    ResponseEntity<CommonApiResponse<List<VoteCardBookmarkResponse>>> findVoteCardBookmark(
            @Parameter(hidden = true) Accessor accessor
    );

    @Operation(
            summary = "[인증 토큰 필요] 사용자가 북마크한 투표들의 카테고리들만 조회 API",
            description = """
                    사용자가 북마크한 투표들의 카테고리들만 조회합니다. ALL 아이콘은 GET /api/v1/categories/icon-data/all 을 통해 iconUrl과 backgroundColor를 확인할 수 있기 때문에 제외하였습니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "카테고리 조회를 성공한 경우 200을 반환합니다.")
    ResponseEntity<CommonApiResponse<List<CategoryResponse>>> findVoteCardBookmarkCategories(
            @Parameter(hidden = true) Accessor accessor
    );

    @Operation(
            summary = "[인증 토큰 필요] 특정 카테고리의 투표 북마크 조회 API",
            description = """
                    categoryId를 path에 담아 보내면 해당 카테고리의 투표 북마크를 조회합니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 조회를 성공한 경우 200을 반환합니다.")
    @ApiResponse(
            responseCode = "400",
            description = "categoryId가 유효하지 않다면 400을 반환합니다.",
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
    ResponseEntity<CommonApiResponse<List<VoteCardBookmarkResponse>>> findVoteCardBookmarkByCategory(
            @Parameter(hidden = true) Accessor accessor,
            @PathVariable(name = "categoryId") final Long categoryId
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

    @Operation(
            summary = "[인증 토큰 필요] 연관 콘텐츠 북마크 제거 API",
            description = """
                    제거할 북마크들의 informationBookmarkId들을 담아 보내면 투표 북마크를 제거합니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 제거에 성공한 경우 200을 반환합니다.")
    @ApiResponse(
            responseCode = "400",
            description = "요청에 포함된 informationBookmarkId에 다른 유저의 북마크가 포함되어있는경우 400을 반환합니다.",
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
    ResponseEntity<CommonApiResponse<Void>> deleteInformationCardBookmarks(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody InformationCardBookmarkDeletionRequest request
    );

    @Operation(
            summary = "[인증 토큰 필요] ALL에 대한 연관 콘텐츠 북마크 조회 API",
            description = """
                    연관 콘텐츠 북마크를 조회합니다. 전체 카테고리에 대한 조회입니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 조회를 성공한 경우 200을 반환합니다.")
    ResponseEntity<CommonApiResponse<List<InformationCardBookmarkResponse>>> findInformationCardBookmark(
            @Parameter(hidden = true) Accessor accessor
    );
}
