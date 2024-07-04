package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;

public record CategoryVotePreviewsResponse(List<SimpleVoteResponse> votes, boolean hasNext, Long nextCursor) {

    public static CategoryVotePreviewsResponse toResponse(
            final List<VoteCard> votes,
            final boolean hasNext,
            final Long nextCursor
    ) {
        return new CategoryVotePreviewsResponse(
                SimpleVoteResponse.toResponse(votes),
                hasNext,
                nextCursor
        );
    }
}
