package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;

public record HotVotePreviewsResponse(SimpleCategoryWithTextColorResponse category, List<SimpleHotVoteResponse> votes) {

    public static HotVotePreviewsResponse toResponse(List<VoteCard> votes) {
        return new HotVotePreviewsResponse(
                new SimpleCategoryWithTextColorResponse(
                        null,
                        "인기 투표",
                        "#344054"
                ),
                SimpleHotVoteResponse.toResponse(votes)
        );
    }
}
