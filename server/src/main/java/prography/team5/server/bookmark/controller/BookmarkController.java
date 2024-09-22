package prography.team5.server.bookmark.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.auth.controller.AuthRequired;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.bookmark.BookmarkApiDocs;
import prography.team5.server.bookmark.service.BookmarkService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmarks")
public class BookmarkController implements BookmarkApiDocs {

    private final BookmarkService bookmarkService;

    /*
    여기는
    투표 콘텐츠
     */
    @PostMapping("/votes")
    public ResponseEntity<CommonApiResponse<EmptyData>> createVoteCardBookmark(
            @AuthRequired Accessor accessor,
            @RequestBody VoteCardBookmarkCreationRequest request
    ) {
        bookmarkService.createVoteCardBookmark(accessor.id(), request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @DeleteMapping("/votes/{voteId}")
    public ResponseEntity<CommonApiResponse<EmptyData>> deleteVoteCardBookmark(
            @AuthRequired Accessor accessor,
            @PathVariable(name = "voteId") final Long voteId
    ) {
        bookmarkService.deleteVoteCardBookmark(accessor.id(), voteId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @PostMapping("/votes/delete")
    public ResponseEntity<CommonApiResponse<EmptyData>> deleteVoteCardBookmarks(
            @AuthRequired Accessor accessor,
            @RequestBody VoteCardBookmarkDeletionRequest request
    ) {
        bookmarkService.deleteVoteCardBookmarks(accessor.id(), request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @GetMapping("/votes")
    public ResponseEntity<CommonApiResponse<VoteCardBookmarkWithCursorResponse>> findVoteCardBookmark(
            @AuthRequired Accessor accessor,
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size
    ) {
        VoteCardBookmarkWithCursorResponse response = bookmarkService.findVoteCardBookmark(accessor.id(), cursor, size);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping("/vote-categories")
    public ResponseEntity<CommonApiResponse<CategoriesWrapper<List<CategoryResponse>>>> findVoteCardBookmarkCategories(
            @AuthRequired Accessor accessor
    ) {
        List<CategoryResponse> response = bookmarkService.findVoteCardBookmarkCategories(accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new CategoriesWrapper<>(response)));
    }

    @GetMapping("/votes/categories/{categoryId}")
    public ResponseEntity<CommonApiResponse<VoteCardBookmarkWithCursorResponse>> findVoteCardBookmarkByCategory(
            @AuthRequired Accessor accessor,
            @PathVariable(name = "categoryId") final Long categoryId,
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size
    ) {
        VoteCardBookmarkWithCursorResponse response = bookmarkService.findVoteCardBookmarkByCategory(accessor.id(),
                categoryId, cursor, size);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    /*
    여기는
    정보 콘텐츠
     */
    @PostMapping("/information")
    public ResponseEntity<CommonApiResponse<EmptyData>> createInformationCardBookmark(
            @AuthRequired Accessor accessor,
            @RequestBody InformationCardBookmarkCreationRequest request
    ) {
        bookmarkService.createInformationCardBookmark(accessor.id(), request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @DeleteMapping("/information/{informationId}")
    public ResponseEntity<CommonApiResponse<EmptyData>> deleteInformationCardBookmark(
            @AuthRequired Accessor accessor,
            @PathVariable(name = "informationId") final Long informationId
    ) {
        bookmarkService.deleteInformationCardBookmark(accessor.id(), informationId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @PostMapping("/information/delete")
    public ResponseEntity<CommonApiResponse<EmptyData>> deleteInformationCardBookmarks(
            @AuthRequired Accessor accessor,
            @RequestBody InformationCardBookmarkDeletionRequest request
    ) {
        bookmarkService.deleteInformationCardBookmarks(accessor.id(), request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @GetMapping("/information")
    public ResponseEntity<CommonApiResponse<InformationCardBookmarkWithCursorResponse>> findInformationCardBookmark(
            @AuthRequired Accessor accessor,
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size
    ) {
        InformationCardBookmarkWithCursorResponse response = bookmarkService.findInformationCardBookmark(accessor.id(), cursor, size);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping("/information-categories")
    public ResponseEntity<CommonApiResponse<CategoriesWrapper<List<CategoryResponse>>>> findInformationCardBookmarkCategories(
            @AuthRequired Accessor accessor
    ) {
        List<CategoryResponse> response = bookmarkService.findInformationCardBookmarkCategories(accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new CategoriesWrapper<>(response)));
    }

    @GetMapping("/information/categories/{categoryId}")
    public ResponseEntity<CommonApiResponse<InformationCardBookmarkWithCursorResponse>> findInformationCardBookmarkByCategory(
            @AuthRequired Accessor accessor,
            @PathVariable(name = "categoryId") final Long categoryId,
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size
    ) {
        InformationCardBookmarkWithCursorResponse response = bookmarkService.findInformationCardBookmarkByCategory(
                accessor.id(),
                categoryId,
                cursor,
                size
        );
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}
