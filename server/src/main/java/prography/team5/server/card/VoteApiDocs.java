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
import prography.team5.server.card.service.dto.SimpleVoteResponse;
import prography.team5.server.card.domain.SortType;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.card.service.dto.CategoryVoteSuggestionsResponse;
import prography.team5.server.card.service.dto.HotVotePreviewsResponse;
import prography.team5.server.card.service.dto.VoteIdResponse;
import prography.team5.server.card.service.dto.VoteRequest;
import prography.team5.server.card.service.dto.VoteResponse;

@Tag(name = "5. 투표 카드", description = "투표 카드 관련 기능입니다.")
public interface VoteApiDocs {

    @Hidden
    @Operation(
            summary = "투표 카드 추가 API",
            description = "투표를 추가할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "투표 추가 성공입니다.")
    ResponseEntity<CommonApiResponse<VoteIdResponse>> create(
            @Parameter(hidden = true) Accessor accessor,
            @RequestBody final VoteRequest voteRequest
    );

    @Hidden
    @Operation(
            summary = "[임시] 단일 투표 카드 조회 API -> 회원의 투표 여부에 따라 다르게 보이도록?",
            description = "투표 id로 해당 투표를 조회할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "투표 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<VoteResponse>> findByVoteId(@PathVariable(value = "voteId") final long voteId);

    //todo: 개발중..
    @Hidden
    @Operation(
            summary = "(카테고리별) 투표 목록 조회 API",
            description = "투표 목록을 전체 조회할 수 있습니다. 투표는 최신순으로 조회됩니다. \n\n"
                    + "category-id 값에 조회하고 싶은 categoryId를 넣으면 해당 카테고리의 투표들만 조회됩니다. \n\n"
                    + "cursor 값으로 마지막 voteId를 전달하면 해당 voteId 이전의 투표를 10개 조회할 수 있습니다. (cursor는 포함X) \n\n"
                    + "cursor 값을 전달하지 않으면 가장 최근에 생성된 투표 10개를 조회합니다.\n\n"
                    + "page-size 값에 조회하고 싶은 투표의 개수를 적으면 해당 개수만큼의 투표들이 조회됩니다. (default 10)"
    )
    @ApiResponse(responseCode = "200", description = "투표 리스트 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<List<SimpleVoteResponse>>> findAll(
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "category-id", required = false) final Long categoryId,
            @RequestParam(name = "page-size", required = false, defaultValue = "10") final Integer pageSize,
            @RequestParam(name = "sort-type", required = false, defaultValue = "LATEST") final SortType sortType
    );

    @Operation(
            summary = "[홈화면] 인기 투표 3개 조회 API",
            description = "- 현재는 최신순으로 3개 조회하는 중, 인기 투표 노출 로직은 추후 수정 예정입니다."
    )
    @ApiResponse(responseCode = "200", description = "투표 리스트 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<HotVotePreviewsResponse>> findHotVotes();

    @Operation(
            summary = "[홈화면] 관심/유저타입 카테고리별로 투표를 3개씩 조회 API",
            description = """
                    기본적으로는 유저의 모든 관심 카테고리별로 투표를 3개씩 조회합니다. \n
                    관심 카테고리가 없다면 유저 타입에 해당하는 모든 카테고리들에 대해 조회합니다. \n
                    현재는 최신순으로 3개 조회하는 중, 투표 노출 로직은 추후 수정 예정입니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "투표 리스트 조회 성공입니다.")
    ResponseEntity<CommonApiResponse<List<CategoryVoteSuggestionsResponse>>> findSuggestions(
            @Parameter(hidden = true) Accessor accessor
    );
}
