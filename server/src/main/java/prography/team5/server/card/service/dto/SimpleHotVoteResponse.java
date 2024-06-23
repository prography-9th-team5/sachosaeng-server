package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.service.dto.CategoryResponse;

public record SimpleHotVoteResponse(Long voteId, String title, Long participantCount, CategoryResponse category) {

    public static SimpleHotVoteResponse toResponse(final VoteCard vote) {
        final Category category = vote.getCategories().get(0);
        return new SimpleHotVoteResponse(
                vote.getId(),
                vote.getTitle(),
                vote.getHotCount(),
                CategoryResponse.toResponse(category)
        );
    }

    public static List<SimpleHotVoteResponse> toResponse(final List<VoteCard> votes) {
        return votes.stream()
                .map(SimpleHotVoteResponse::toResponse)
                .toList();
    }
}
