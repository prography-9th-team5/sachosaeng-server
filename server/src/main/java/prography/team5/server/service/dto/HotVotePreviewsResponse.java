package prography.team5.server.service.dto;

import java.util.List;
import prography.team5.server.domain.card.VoteCard;

public record HotVotePreviewsResponse(SimpleCategoryWithTextColorResponse category, List<SimpleVoteWithIconResponse> votes) {

    public static HotVotePreviewsResponse toResponse(List<VoteCard> votes) {
        return new HotVotePreviewsResponse(
                new SimpleCategoryWithTextColorResponse(
                        null,
                        "인기 투표",
                        "#344054"
                ),
                SimpleVoteWithIconResponse.toResponse(votes)
        );
    }
}
