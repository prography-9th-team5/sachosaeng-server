package prography.team5.server.bookmark.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.auth.controller.AuthRequired;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.bookmark.BookmarkApiDocs;
import prography.team5.server.bookmark.service.BookmarkService;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkResponse;
import prography.team5.server.category.service.dto.CategoryResponse;
import prography.team5.server.common.CommonApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmarks")
public class BookmarkController implements BookmarkApiDocs {

    private final BookmarkService bookmarkService;

    @PostMapping("/votes")
    public ResponseEntity<CommonApiResponse<Void>> createVoteCardBookmark(
            @AuthRequired Accessor accessor,
            @RequestBody VoteCardBookmarkCreationRequest request
    ) {
        bookmarkService.createVoteCardBookmark(accessor.id(), request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }

    @GetMapping("/votes")
    public ResponseEntity<CommonApiResponse<List<VoteCardBookmarkResponse>>> findVoteCardBookmark(
            @AuthRequired Accessor accessor
    ) {
        List<VoteCardBookmarkResponse> response = bookmarkService.findVoteCardBookmark(accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @GetMapping("/vote-categories")
    public ResponseEntity<CommonApiResponse<List<CategoryResponse>>> findVoteCardBookmarkCategories(
            @AuthRequired Accessor accessor
    ) {
        List<CategoryResponse> response = bookmarkService.findVoteCardBookmarkCategories(accessor.id());
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PostMapping("/information")
    public ResponseEntity<CommonApiResponse<Void>> createInformationCardBookmark(
            @AuthRequired Accessor accessor,
            @RequestBody InformationCardBookmarkCreationRequest request
    ) {
        bookmarkService.createInformationCardBookmark(accessor.id(), request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
