package prography.team5.server.service.dto;

import java.util.List;
import prography.team5.server.domain.card.VoteCard;
import prography.team5.server.domain.category.Category;

public record CategoryVoteSuggestionsResponse(SimpleCategoryWithTextColorResponse category, List<SimpleVoteResponse> votes) {

    public static CategoryVoteSuggestionsResponse toResponse(final Category category, final List<VoteCard> votes) {
        return new CategoryVoteSuggestionsResponse(
                SimpleCategoryWithTextColorResponse.toResponse(category),
                SimpleVoteResponse.toResponse(votes)
        );
    }
}
