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
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.auth.controller.AuthRequired;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.bookmark.BookmarkApiDocs;
import prography.team5.server.bookmark.service.BookmarkService;
import prography.team5.server.common.InformationWrapper;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkDeletionRequest;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkResponse;
import prography.team5.server.common.VotesWrapper;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkDeletionRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkResponse;
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

    @DeleteMapping("/votes")
    public ResponseEntity<CommonApiResponse<EmptyData>> deleteVoteCardBookmarks(
            @AuthRequired Accessor accessor,
            @RequestBody VoteCardBookmarkDeletionRequest request
    ) {
        bookmarkService.deleteVoteCardBookmarks(accessor.id(), request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @GetMapping("/votes")
    public ResponseEntity<CommonApiResponse<VotesWrapper<List<VoteCardBookmarkResponse>>>> findVoteCardBookmark(
            @AuthRequired Accessor accessor
    ) {
        List<VoteCardBookmarkResponse> response = bookmarkService.findVoteCardBookmark(accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new VotesWrapper<>(response)));
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
    public ResponseEntity<CommonApiResponse<VotesWrapper<List<VoteCardBookmarkResponse>>>> findVoteCardBookmarkByCategory(
            @AuthRequired Accessor accessor,
            @PathVariable(name = "categoryId") final Long categoryId
    ) {
        List<VoteCardBookmarkResponse> response = bookmarkService.findVoteCardBookmarkByCategory(accessor.id(),
                categoryId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new VotesWrapper<>(response)));
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

    @DeleteMapping("/information")
    public ResponseEntity<CommonApiResponse<EmptyData>> deleteInformationCardBookmarks(
            @AuthRequired Accessor accessor,
            @RequestBody InformationCardBookmarkDeletionRequest request
    ) {
        bookmarkService.deleteInformationCardBookmarks(accessor.id(), request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new EmptyData()));
    }

    @GetMapping("/information")
    public ResponseEntity<CommonApiResponse<InformationWrapper<List<InformationCardBookmarkResponse>>>> findInformationCardBookmark(
            @AuthRequired Accessor accessor
    ) {
        List<InformationCardBookmarkResponse> response = bookmarkService.findInformationCardBookmark(accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new InformationWrapper<>(response)));
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
    public ResponseEntity<CommonApiResponse<InformationWrapper<List<InformationCardBookmarkResponse>>>> findInformationCardBookmarkByCategory(
            @AuthRequired Accessor accessor,
            @PathVariable(name = "categoryId") final Long categoryId
    ) {
        List<InformationCardBookmarkResponse> response = bookmarkService.findInformationCardBookmarkByCategory(accessor.id(),
                categoryId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", new InformationWrapper<>(response)));
    }
}
