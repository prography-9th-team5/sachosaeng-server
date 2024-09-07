package prography.team5.server.bookmark.service.dto;

import java.util.List;

public record InformationCardBookmarkDeletionRequest(
        List<Long> informationBookmarkIds
) {

}
