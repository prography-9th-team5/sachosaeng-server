package prography.team5.server.card.service.dto;

import java.util.List;
import java.util.Map;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.HotVotesDesign;
import prography.team5.server.category.service.dto.CategoryResponse;

public record HotVotePreviewsResponse(CategoryResponse category, List<SimpleVoteWithCategoryResponse> votes) {

    public static HotVotePreviewsResponse toResponse(List<VoteCard> votes, HotVotesDesign hotVotesDesign, final Map<Long, Boolean> isVotedAnalysis) {
        return new HotVotePreviewsResponse(
                new CategoryResponse(
                        null,
                        hotVotesDesign.getName(),
                        hotVotesDesign.getIconUrl(),
                        null,
                        hotVotesDesign.getTextColor()
                ),
                SimpleVoteWithCategoryResponse.toHotVoteResponse(votes, isVotedAnalysis)
        );
    }
}
