package prography.team5.server.service.dto;

import java.util.List;
import prography.team5.server.domain.card.VoteCard;
import prography.team5.server.domain.category.Category;

public record CategoryVotePreviewsResponse(SimpleCategoryWithTextColorResponse category, List<SimpleVoteResponse> votes) {

    public static CategoryVotePreviewsResponse toResponse(final Category category, final List<VoteCard> votes) {
        return new CategoryVotePreviewsResponse(
                SimpleCategoryWithTextColorResponse.toResponse(category),
                SimpleVoteResponse.toResponse(votes)
        );
    }
}
