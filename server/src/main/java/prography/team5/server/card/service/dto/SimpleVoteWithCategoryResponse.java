package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.service.dto.CategoryResponse;

public record SimpleVoteWithCategoryResponse(Long voteId, String title, Long participantCount, boolean isVoted, CategoryResponse category) {

    public static SimpleVoteWithCategoryResponse toHotVoteResponse(final VoteCard vote) {
        final Category category = vote.getCategories().get(0);
        return new SimpleVoteWithCategoryResponse(
                vote.getId(),
                vote.getTitle(),
                vote.getHotParticipantCount(),
                false, // todo: 일단은 하드코딩!
                CategoryResponse.toResponseWith18px(category)
        );
    }

    public static List<SimpleVoteWithCategoryResponse> toHotVoteResponse(final List<VoteCard> votes) {
        return votes.stream()
                .map(SimpleVoteWithCategoryResponse::toHotVoteResponse)
                .toList();
    }

    public static SimpleVoteWithCategoryResponse toResponseWith18px(final VoteCard vote) {
        final Category category = vote.getCategories().get(0);
        return new SimpleVoteWithCategoryResponse(
                vote.getId(),
                vote.getTitle(),
                vote.getParticipantCount(),
                false, // todo: 일단은 하드코딩!
                CategoryResponse.toResponseWith18px(category)
        );
    }
}
