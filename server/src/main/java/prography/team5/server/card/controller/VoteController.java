package prography.team5.server.card.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.auth.controller.AuthRequired;
import prography.team5.server.card.service.dto.CategoryVotePreviewsResponse;
import prography.team5.server.card.service.dto.MyVotePreviewsResponse;
import prography.team5.server.card.service.dto.MyVoteResponse;
import prography.team5.server.card.service.dto.SimpleVoteResponse;
import prography.team5.server.card.domain.SortType;
import prography.team5.server.card.service.dto.VoteCreationRequest;
import prography.team5.server.card.service.dto.VoteIdResponse;
import prography.team5.server.card.service.dto.VoteOptionChoiceRequest;
import prography.team5.server.common.CategoriesWrapper;
import prography.team5.server.common.CommonApiResponse;
import prography.team5.server.card.VoteApiDocs;
import prography.team5.server.card.service.VoteService;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.card.service.dto.CategoryVoteSuggestionsResponse;
import prography.team5.server.card.service.dto.VoteResponse;
import prography.team5.server.common.EmptyData;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/votes")
public class VoteController implements VoteApiDocs {

    private final VoteService voteService;

    //todo: 이거는 로그인 안하면 못봄
    @GetMapping("/{voteId}")
    public ResponseEntity<CommonApiResponse<VoteResponse>> findByVoteId(
            @AuthRequired Accessor accessor,
            @PathVariable(value = "voteId") final long voteId,
            @RequestParam(value = "category-id", required = false) final Long categoryId
    ) {
        final VoteResponse response = voteService.findByVoteId(accessor.id(), voteId, categoryId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CommonApiResponse<CategoryVotePreviewsResponse>> findAllByCategoryId(
            @AuthRequired(required = false) Accessor accessor,
            @PathVariable(name = "categoryId") final Long categoryId,
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "size", required = false, defaultValue = "10")  final Integer size) {
        CategoryVotePreviewsResponse response = voteService.findAllByCategoryId(accessor, cursor, categoryId, size);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    // todo: 레거시
    @GetMapping
    public ResponseEntity<CommonApiResponse<List<SimpleVoteResponse>>> findAll(
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "category-id", required = false) final Long categoryId,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size,
            @RequestParam(name = "sort-type", required = false, defaultValue = "LATEST") final SortType sortType
            ) {
        final List<SimpleVoteResponse> response = voteService.findAll(cursor, categoryId, size, sortType);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping("/suggestions/all")
    public ResponseEntity<CommonApiResponse<CategoriesWrapper<List<CategoryVoteSuggestionsResponse>>>> findSuggestionsOfAllCategories(
            @AuthRequired(required = false) Accessor accessor
    ) {
        final List<CategoryVoteSuggestionsResponse> response = voteService.findSuggestionsOfAllCategories(accessor);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new CategoriesWrapper<>(response)));
    }

    @GetMapping("/suggestions/my")
    public ResponseEntity<CommonApiResponse<CategoriesWrapper<List<CategoryVoteSuggestionsResponse>>>> findSuggestionsOfMy(
            @AuthRequired Accessor accessor
    ) {
        final List<CategoryVoteSuggestionsResponse> response = voteService.findSuggestionsOfMy(accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new CategoriesWrapper<>(response)));
    }

    @PutMapping("/{voteId}/choices")
    public ResponseEntity<CommonApiResponse<EmptyData>> chooseVoteOption(
            @AuthRequired Accessor accessor,
            @PathVariable(value = "voteId") final long voteId,
            @RequestBody final VoteOptionChoiceRequest request
    ) {
        voteService.chooseVoteOption(accessor.id(), voteId, request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @PostMapping
    public ResponseEntity<CommonApiResponse<VoteIdResponse>> create(
            @AuthRequired Accessor accessor,
            @RequestBody final VoteCreationRequest request
    ) {
        final VoteIdResponse response = voteService.create(request, accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping("/my")
    public ResponseEntity<CommonApiResponse<MyVotePreviewsResponse>> findMyVotes(
            @AuthRequired Accessor accessor,
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size
    ) {
        final MyVotePreviewsResponse response = voteService.findMyVotes(accessor.id(), cursor, size);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping("/my/{voteId}")
    public ResponseEntity<CommonApiResponse<MyVoteResponse>> findMyVote(
            @AuthRequired Accessor accessor,
            @PathVariable(value = "voteId") final long voteId
    ) {
        final MyVoteResponse response = voteService.findMyVote(accessor.id(), voteId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}
