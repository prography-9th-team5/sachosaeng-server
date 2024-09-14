package prography.team5.server.bookmark.service.dto;

import java.util.List;

public record InformationCardBookmarkWithCursorResponse(
        List<InformationCardBookmarkResponse> information,
        boolean hasNext,
        Long nextCursor
) {

}
