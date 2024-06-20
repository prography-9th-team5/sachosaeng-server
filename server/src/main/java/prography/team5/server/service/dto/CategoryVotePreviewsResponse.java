package prography.team5.server.service.dto;

import java.util.List;
import prography.team5.server.domain.card.VoteCard;
import prography.team5.server.domain.category.Category;

public record CategoryVotePreviewsResponse(CategoryResponse category, List<BaseVoteResponse> votes) {

    public static CategoryVotePreviewsResponse toResponse(final Category category, final List<VoteCard> votes) {
        return new CategoryVotePreviewsResponse(
                CategoryResponse.toResponse(category),
                BaseVoteResponse.toResponse(votes)
        );
    }
}
