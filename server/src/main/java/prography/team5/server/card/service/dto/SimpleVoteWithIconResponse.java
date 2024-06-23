package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.service.dto.CategoryResponse;

public record SimpleVoteWithIconResponse(Long voteId, String title, Integer participantCount, CategoryResponse category) {

    public static SimpleVoteWithIconResponse toResponse(final VoteCard vote) {
        final Category category = vote.getCategories().get(0);
        return new SimpleVoteWithIconResponse(
                vote.getId(),
                vote.getTitle(),
                null,
                CategoryResponse.toResponse(category)
        );
    }

    public static List<SimpleVoteWithIconResponse> toResponse(final List<VoteCard> votes) {
        return votes.stream()
                .map(SimpleVoteWithIconResponse::toResponse)
                .toList();
    }
}
