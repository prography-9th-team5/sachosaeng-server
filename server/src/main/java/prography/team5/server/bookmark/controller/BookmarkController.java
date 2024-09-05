package prography.team5.server.bookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
import prography.team5.server.card.service.dto.VoteResponse;
import prography.team5.server.common.CommonApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmarks")
public class BookmarkController implements BookmarkApiDocs {

    private final BookmarkService bookmarkService;

    @PostMapping("/votes")
    public ResponseEntity<CommonApiResponse<VoteResponse>> createVoteCardBookmark(
            @AuthRequired Accessor accessor,
            @RequestBody VoteCardBookmarkCreationRequest request
    ) {
        bookmarkService.createVoteCardBookmark(accessor.id(), request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }

    @PostMapping("/information")
    public ResponseEntity<CommonApiResponse<VoteResponse>> createInformationCardBookmark(
            @AuthRequired Accessor accessor,
            @RequestBody InformationCardBookmarkCreationRequest request
    ) {
        bookmarkService.createInformationCardBookmark(accessor.id(), request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
