package prography.team5.server.bookmark.service.dto;

import java.util.List;
import prography.team5.server.bookmark.domain.VoteCardBookmark;

public record VoteCardBookmarkResponse(
        Long voteId,
        String title,
        String description
) {

    private static VoteCardBookmarkResponse toResponse(final VoteCardBookmark bookmark) {
        return new VoteCardBookmarkResponse(
                bookmark.getVoteCard().getId(),
                bookmark.getVoteCard().getTitle(),
                "임시 문구입니다"
        );
    }

    public static List<VoteCardBookmarkResponse> toResponse(final List<VoteCardBookmark> bookmarks) {
        return bookmarks.stream().map(VoteCardBookmarkResponse::toResponse).toList();
    }
}
