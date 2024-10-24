package prography.team5.server.card;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import prography.team5.server.auth.controller.AuthRequired;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.card.domain.SortType;
import prography.team5.server.card.service.dto.CategoryVotePreviewsResponse;
import prography.team5.server.card.service.dto.CategoryVoteSuggestionsResponse;
import prography.team5.server.card.service.dto.MyVotePreviewsResponse;
import prography.team5.server.card.service.dto.MyVoteResponse;
import prography.team5.server.card.service.dto.SimpleVoteResponse;
import prography.team5.server.card.service.dto.VoteCreationRequest;
import prography.team5.server.card.service.dto.VoteIdResponse;
import prography.team5.server.card.service.dto.VoteOptionChoiceRequest;
import prography.team5.server.card.service.dto.VoteResponse;
import prography.team5.server.common.CategoriesWrapper;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.common.EmptyData;

@Tag(name = "05. 투표 카드", description = "투표 카드 관련 기능입니다.")
public interface VoteApiDocs {

    @Operation(
            summary = "[인증 토큰 필요] 단일 투표 카드 조회 API",
            description = """
                    투표 id로 해당 투표를 조회할 수 있습니다. \n
                    유저의 투표 여부, 옵션별 투표수, 전체 투표수를 포함하여 반환합니다. \n
                    하나의 투표가 여러개의 카테고리에 속하는 경우가 있을 수 있으므로
                     - 파라미터로 category-id를 넘겨주면 해당 카테고리 정보를 포함해 반환합니다. (ex. 특정 카테고리에서 진입한 경우)
                     - category-id가 없다면 카드가 포함하는 카테고리 중 가장 첫번째 카테고리가 포함되어 반환됩니다. (ex. 전체 인기 투표를 통해 진입한 경우) \n
                    !! 연관 콘텐츠는 아직 추가되지 않았습니다. !!
                    """
    )
    @ApiResponse(responseCode = "200", description = "투표 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<VoteResponse>> findByVoteId(
            @Parameter(hidden = true) Accessor accessor,
            @PathVariable(value = "voteId") final long voteId,
            @RequestParam(value = "category-id", required = false) final Long categoryId
    );

    @Operation(
            summary = "카테고리별 투표 목록 조회 API",
            description = "해당 카테고리의 투표 목록을 최신순으로 조회할 수 있습니다. \n\n"
                    + "categoryId 값에 조회하고 싶은 categoryId를 넣으면 해당 카테고리의 투표들만 조회됩니다. \n\n"
                    + "size 값에 조회하고 싶은 투표의 개수를 적으면 해당 개수만큼의 투표들이 조회됩니다. (default=10) \n\n"
                    + "(첫 조회시) cursor 값을 전달하지 않으면 가장 최근에 생성된 투표 10개를 조회합니다.\n\n"
                    + "응답에는 다음 조회할 투표들이 남아 있는지 여부(hasNext)와, 다음 조회시에 사용할 커서(nextCursor)가 포함되어 있습니다. 여기서 nextCursor는 현재 응답의 마지막 voteId입니다. \n\n"
                    + "(두번째 이후 조회시) cursor 값으로 이전 응답의 nextCursor를 전달하면 cursor 다음의 투표를 10개 조회할 수 있습니다. \n\n"
                    + "즉, cursor는 포함되지 않고 새로 10개가 조회됩니다. \n\n"
    )
    @ApiResponse(responseCode = "200", description = "투표 리스트 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<CategoryVotePreviewsResponse>> findAllByCategoryId(
            @Parameter(hidden = true) Accessor accessor,
            @PathVariable(name = "categoryId") final Long categoryId,
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size
    );


    @Hidden
    ResponseEntity<CommonApiResponse<List<SimpleVoteResponse>>> findAll(
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "category-id", required = false) final Long categoryId,
            @RequestParam(name = "page-size", required = false, defaultValue = "10") final Integer pageSize,
            @RequestParam(name = "sort-type", required = false, defaultValue = "LATEST") final SortType sortType
    );

    @Operation(
            summary = "[홈화면] 전체 카테고리 투표를 3개씩 조회 API -> []를 {}로 감쌌어요!!",
            description = """
                    현재는 각 카테고리에 대해 최신순으로 3개 조회하는 중, 투표 노출 로직은 추후 수정 예정입니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "투표 리스트 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<CategoriesWrapper<List<CategoryVoteSuggestionsResponse>>>> findSuggestionsOfAllCategories(
            @Parameter(hidden = true) Accessor accessor
    );

    @Operation(
            summary = "[인증 토큰 필요][홈화면] 관심/유저타입 카테고리별로 투표를 3개씩 조회 API -> []를 {}로 감쌌어요!!",
            description = """
                    기본적으로는 유저의 모든 관심 카테고리별로 투표를 3개씩 조회합니다. \n
                    관심 카테고리가 없다면 유저 타입에 해당하는 모든 카테고리들에 대해 조회합니다. \n
                    현재는 최신순으로 3개 조회하는 중, 투표 노출 로직은 추후 수정 예정입니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "투표 리스트 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<CategoriesWrapper<List<CategoryVoteSuggestionsResponse>>>> findSuggestionsOfMy(
            @Parameter(hidden = true) Accessor accessor
    );

    @Operation(
            summary = "[인증 토큰 필요] 투표 옵션 선택/변경 API",
            description = """
                    선택된 투표 옵션들의 id를 모두 담아 보내 투표를 합니다. \n
                    이미 투표를 한 후 투표 옵션 변경에도 해당 API를 사용할 수 있습니다.
                    변경시 변경 후에 선택되어있는 모든 id를 담아 보내야하며, 이전 투표내역 전체를 대체합니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "투표 리스트 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<EmptyData>> chooseVoteOption(
            @Parameter(hidden = true) Accessor accessor,
            @PathVariable(value = "voteId") final long voteId,
            @RequestBody final VoteOptionChoiceRequest request
    );

    @Operation(
            summary = "[인증 토큰 필요] 투표 추가 요청 API",
            description = """
                    사용자는 투표 추가를 요청할 수 있습니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "투표 추가 요청 성공입니다.")
    ResponseEntity<CommonApiResponse<VoteIdResponse>> create(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody final VoteCreationRequest request
    );

    @Operation(
            summary = "[인증 토큰 필요] 내가 등록한 투표 히스토리 조회 API",
            description = """
                    사용자는 자신이 등록한 투표의 히스토리를 조회할 수 있습니다. status는 등록 상태를 나타내며, PENDING -> APPROVED/REJECTED 로 구분됩니다. \n
                    첫 요청이후엔, 반드시 응답의 lastCursor를 다음 요청의 cursor에 포함시켜서 보내주세요.
                    """
    )
    @ApiResponse(responseCode = "200", description = "투표 히스토리 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<MyVotePreviewsResponse>> findMyVotes(
            @Parameter(hidden = true) Accessor accessor,
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size
    );

    @Operation(
            summary = "[인증 토큰 필요] 내가 등록한 투표 단일 조회 API",
            description = """
                    사용자는 자신이 등록한 투표의 상세 내역을 조회할 수 있습니다. status는 등록 상태를 나타내며, PENDING -> APPROVED/REJECTED 로 구분됩니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "단일 투표 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<MyVoteResponse>> findMyVote(
            @Parameter(hidden = true) Accessor accessor,
            @PathVariable(value = "voteId") final long voteId
    );
}
