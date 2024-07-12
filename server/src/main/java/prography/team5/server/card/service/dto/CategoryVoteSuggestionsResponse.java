package prography.team5.server.card.service.dto;

import java.util.List;
import java.util.Map;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.service.dto.CategoryResponse;

public record CategoryVoteSuggestionsResponse(CategoryResponse category, List<SimpleVoteResponse> votes) {

    public static CategoryVoteSuggestionsResponse toResponse(final Category category, final List<VoteCard> votes, final Map<Long, Boolean> isVotedAnalysis) {
        return new CategoryVoteSuggestionsResponse(
                CategoryResponse.toResponseWith18px(category),
                SimpleVoteResponse.toResponse(votes, isVotedAnalysis)
        );
    }
}
