package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.service.dto.CategoryResponse;

public record VoteResponse(
        Long voteId,
        boolean isClosed,
        boolean isVoted,
        List<Long> chosenVoteOptionId,
        CategoryResponse category,
        String title,
        Long participantCount,
        List<VoteOptionResponse> voteOptions,
        String description
) {

    public static VoteResponse toResponseWith32px(
            final Category category,
            final boolean isVoted,
            final List<Long> voteOptionId,
            final VoteCard voteCard,
            final String description
    ) {
        return new VoteResponse(
                voteCard.getId(),
                voteCard.isClosed(),
                isVoted,
                voteOptionId,
                CategoryResponse.toResponseWith32px(category),
                voteCard.getTitle(),
                voteCard.getParticipantCount(),
                voteCard.getVoteOptions()
                        .stream()
                        .map(option -> new VoteOptionResponse(option.getId(), option.getContent(), option.getCount()))
                        .toList(),
                description

        );
    }
}
