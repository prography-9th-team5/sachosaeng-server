package prography.team5.server.bookmark.service.dto;

import java.util.List;
import java.util.Map;
import prography.team5.server.bookmark.domain.VoteCardBookmark;

public record VoteCardBookmarkResponse(
        Long voteBookmarkId,
        Long voteId,
        String title,
        String description
) {

    private static VoteCardBookmarkResponse toResponse(final VoteCardBookmark bookmark, final String description) {
        return new VoteCardBookmarkResponse(
                bookmark.getId(),
                bookmark.getVoteCard().getId(),
                bookmark.getVoteCard().getTitle(),
                description
        );
    }

    public static List<VoteCardBookmarkResponse> toResponse(final List<VoteCardBookmark> bookmarks, final Map<Long, String> descriptions) {
        return bookmarks.stream().map(each -> toResponse(each, descriptions.getOrDefault(each.getVoteCard().getId(), ""))).toList();
    }
}
