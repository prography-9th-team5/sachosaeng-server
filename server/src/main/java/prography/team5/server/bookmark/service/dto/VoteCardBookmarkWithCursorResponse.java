package prography.team5.server.bookmark.service.dto;

import java.util.List;

public record VoteCardBookmarkWithCursorResponse(
        List<VoteCardBookmarkResponse> votes,
        boolean hasNext,
        Long nextCursor
) {

}
