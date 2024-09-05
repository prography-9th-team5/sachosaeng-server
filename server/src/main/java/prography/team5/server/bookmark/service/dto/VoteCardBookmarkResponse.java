package prography.team5.server.bookmark.service.dto;

import java.util.List;
import prography.team5.server.bookmark.domain.VoteCardBookmark;

public record VoteCardBookmarkResponse(
        Long voteId,
        String title,
        String description
) {

    private static VoteCardBookmarkResponse from(final VoteCardBookmark bookmark) {
        return new VoteCardBookmarkResponse(
                bookmark.getVoteCard().getId(),
                bookmark.getVoteCard().getTitle(),
                ""
        );
    }

    public static List<VoteCardBookmarkResponse> from(final List<VoteCardBookmark> bookmarks) {
        return bookmarks.stream().map(VoteCardBookmarkResponse::from).toList();
    }
}
