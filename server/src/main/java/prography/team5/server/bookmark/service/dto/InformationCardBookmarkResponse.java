package prography.team5.server.bookmark.service.dto;

import java.util.List;
import prography.team5.server.bookmark.domain.InformationCardBookmark;

public record InformationCardBookmarkResponse(
        Long informationBookmarkId,
        Long informationId,
        String title
) {

    private static InformationCardBookmarkResponse toResponse(final InformationCardBookmark bookmark) {
        return new InformationCardBookmarkResponse(
                bookmark.getId(),
                bookmark.getInformationCard().getId(),
                bookmark.getInformationCard().getTitle()
        );
    }

    public static List<InformationCardBookmarkResponse> toResponse(final List<InformationCardBookmark> bookmarks) {
        return bookmarks.stream().map(InformationCardBookmarkResponse::toResponse).toList();
    }
}
