package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;

public record MyVotePreviewsResponse(List<MyVotePreviewResponse> votes, boolean hasNext, Long nextCursor) {

    public static MyVotePreviewsResponse toResponse(List<VoteCard> votes, boolean hasNext, Long nextCursor) {
        return new MyVotePreviewsResponse(
                MyVotePreviewResponse.toResponse(votes),
                hasNext,
                nextCursor
        );
    }
}
