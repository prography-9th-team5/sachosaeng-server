package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.service.dto.CategoryResponse;

public record SimpleHotVoteResponse(Long voteId, String title, Long participantCount, boolean isVoted, CategoryResponse category) {

    public static SimpleHotVoteResponse toResponseWith18px(final VoteCard vote) {
        final Category category = vote.getCategories().get(0);
        return new SimpleHotVoteResponse(
                vote.getId(),
                vote.getTitle(),
                vote.getHotParticipantCount(),
                false, // todo: 일단은 하드코딩!
                CategoryResponse.toResponseWith18px(category)
        );
    }

    public static List<SimpleHotVoteResponse> toResponseWith18px(final List<VoteCard> votes) {
        return votes.stream()
                .map(SimpleHotVoteResponse::toResponseWith18px)
                .toList();
    }
}
