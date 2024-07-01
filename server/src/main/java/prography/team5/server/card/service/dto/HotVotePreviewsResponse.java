package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.HotVotesDesign;
import prography.team5.server.category.service.dto.CategoryResponse;

public record HotVotePreviewsResponse(CategoryResponse category, List<SimpleHotVoteResponse> votes) {

    public static HotVotePreviewsResponse toResponse(List<VoteCard> votes, HotVotesDesign hotVotesDesign) {
        return new HotVotePreviewsResponse(
                new CategoryResponse(
                        null,
                        hotVotesDesign.getName(),
                        hotVotesDesign.getIconUrl(),
                        null,
                        hotVotesDesign.getTextColor()
                ),
                SimpleHotVoteResponse.toResponseWith18px(votes)
        );
    }
}
