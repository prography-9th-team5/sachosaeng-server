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
import org.springframework.web.bind.annotation.RequestParam;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkDeletionRequest;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkWithCursorResponse;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkDeletionRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkWithCursorResponse;
import prography.team5.server.category.service.dto.CategoryResponse;
import prography.team5.server.common.CategoriesWrapper;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.common.EmptyData;

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
                                          "data": {}
                                        }
                                    """
                    )
            )
    )
    ResponseEntity<CommonApiResponse<EmptyData>> createVoteCardBookmark(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody VoteCardBookmarkCreationRequest request
    );

    @Operation(
            summary = "[인증 토큰 필요] voteId로 개별 투표 북마크 삭제 API",
            description = """
                    개별 투표 북마크를 삭제합니다. 요청에 path에 voteId를 담아 보내면 북마크를 삭제할 수 있습니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 삭제를 성공한 경우 200을 반환합니다.")
    ResponseEntity<CommonApiResponse<EmptyData>> deleteVoteCardBookmark(
            @Parameter(hidden = true) Accessor accessor,
            @PathVariable(name = "voteId") final Long voteId
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
                                          "data": {}
                                        }
                                    """
                    )
            )
    )
    ResponseEntity<CommonApiResponse<EmptyData>> deleteVoteCardBookmarks(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody VoteCardBookmarkDeletionRequest request
    );

    @Operation(
            summary = "[인증 토큰 필요] ALL에 대한 투표 북마크 조회 API -> []를 {}로 감쌌어요!!",
            description = """
                    투표 북마크를 조회합니다. 전체 카테고리에 대한 조회입니다. \n
                    조회하는 기본 북마크수(size)를 별도로 설정하지 않으면 디폴트값인 10으로 조회됩니다. \n
                    cursor를 별도로 설정하지 않으면 가장 최신의 북마크를 size만큼 조회합니다. 다음 요청부터는 응답에 포함된 nextCursor를 cursor에 담아보내면 됩니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 조회를 성공한 경우 200을 반환합니다.")
    ResponseEntity<CommonApiResponse<VoteCardBookmarkWithCursorResponse>> findVoteCardBookmark(
            @Parameter(hidden = true) Accessor accessor,
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size
    );

    @Operation(
            summary = "[인증 토큰 필요] 사용자가 북마크한 투표들의 카테고리들만 조회 API -> []를 {}로 감쌌어요!!",
            description = """
                    사용자가 북마크한 투표들의 카테고리들만 조회합니다. ALL 아이콘은 GET /api/v1/categories/icon-data/all 을 통해 iconUrl과 backgroundColor를 확인할 수 있기 때문에 제외하였습니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "카테고리 조회를 성공한 경우 200을 반환합니다.")
    ResponseEntity<CommonApiResponse<CategoriesWrapper<List<CategoryResponse>>>> findVoteCardBookmarkCategories(
            @Parameter(hidden = true) Accessor accessor
    );

    @Operation(
            summary = "[인증 토큰 필요] 특정 카테고리의 투표 북마크 조회 API -> []를 {}로 감쌌어요!!",
            description = """
                    categoryId를 path에 담아 보내면 해당 카테고리의 투표 북마크를 조회합니다. \n
                    조회하는 기본 북마크수(size)를 별도로 설정하지 않으면 디폴트값인 10으로 조회됩니다. \n
                    cursor를 별도로 설정하지 않으면 가장 최신의 북마크를 size만큼 조회합니다. 다음 요청부터는 응답에 포함된 nextCursor를 cursor에 담아보내면 됩니다.
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
                                          "data": {}
                                        }
                                    """
                    )
            )
    )
    ResponseEntity<CommonApiResponse<VoteCardBookmarkWithCursorResponse>> findVoteCardBookmarkByCategory(
            @Parameter(hidden = true) Accessor accessor,
            @PathVariable(name = "categoryId") final Long categoryId,
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size
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
                                          "data": {}
                                        }
                                    """
                    )
            )
    )
    ResponseEntity<CommonApiResponse<EmptyData>> createInformationCardBookmark(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody InformationCardBookmarkCreationRequest request
    );

    @Operation(
            summary = "[인증 토큰 필요] informationId로 개별 연관콘텐츠 북마크 삭제 API",
            description = """
                    연관 콘텐츠의 개별 북마크를 삭제합니다. 요청에 path에 informationId를 담아 보내면 북마크를 삭제할 수 있습니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 삭제를 성공한 경우 200을 반환합니다.")
    ResponseEntity<CommonApiResponse<EmptyData>> deleteInformationCardBookmark(
            @Parameter(hidden = true) Accessor accessor,
            @PathVariable(name = "informationId") final Long informationId
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
                                          "data": {}
                                        }
                                    """
                    )
            )
    )
    ResponseEntity<CommonApiResponse<EmptyData>> deleteInformationCardBookmarks(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody InformationCardBookmarkDeletionRequest request
    );

    @Operation(
            summary = "[인증 토큰 필요] ALL에 대한 연관 콘텐츠 북마크 조회 API -> []를 {}로 감쌌어요!!",
            description = """
                    연관 콘텐츠 북마크를 조회합니다. 전체 카테고리에 대한 조회입니다. \n
                    조회하는 기본 북마크수(size)를 별도로 설정하지 않으면 디폴트값인 10으로 조회됩니다. \n
                    cursor를 별도로 설정하지 않으면 가장 최신의 북마크를 size만큼 조회합니다. 다음 요청부터는 응답에 포함된 nextCursor를 cursor에 담아보내면 됩니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "북마크 조회를 성공한 경우 200을 반환합니다.")
    ResponseEntity<CommonApiResponse<InformationCardBookmarkWithCursorResponse>> findInformationCardBookmark(
            @Parameter(hidden = true) Accessor accessor,
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size
    );

    @Operation(
            summary = "[인증 토큰 필요] 사용자가 북마크한 연관 콘텐츠들의 카테고리들만 조회 API -> []를 {}로 감쌌어요!!",
            description = """
                    사용자가 북마크한 연관 콘텐츠들의 카테고리들만 조회합니다. ALL 아이콘은 GET /api/v1/categories/icon-data/all 을 통해 iconUrl과 backgroundColor를 확인할 수 있기 때문에 제외하였습니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "카테고리 조회를 성공한 경우 200을 반환합니다.")
    ResponseEntity<CommonApiResponse<CategoriesWrapper<List<CategoryResponse>>>> findInformationCardBookmarkCategories(
            @Parameter(hidden = true) Accessor accessor
    );

    @Operation(
            summary = "[인증 토큰 필요] 특정 카테고리의 연관 콘텐츠 북마크 조회 API -> []를 {}로 감쌌어요!!",
            description = """
                    categoryId를 path에 담아 보내면 해당 카테고리의 연관 콘텐츠 북마크를 조회합니다. \n
                    조회하는 기본 북마크수(size)를 별도로 설정하지 않으면 디폴트값인 10으로 조회됩니다. \n
                    cursor를 별도로 설정하지 않으면 가장 최신의 북마크를 size만큼 조회합니다. 다음 요청부터는 응답에 포함된 nextCursor를 cursor에 담아보내면 됩니다.
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
                                          "data": {}
                                        }
                                    """
                    )
            )
    )
    ResponseEntity<CommonApiResponse<InformationCardBookmarkWithCursorResponse>> findInformationCardBookmarkByCategory(
            @Parameter(hidden = true) Accessor accessor,
            @PathVariable(name = "categoryId") final Long categoryId,
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size
    );
}
