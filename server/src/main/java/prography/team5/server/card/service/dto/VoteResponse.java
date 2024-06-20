package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.service.dto.SimpleCategoryResponse;

public record VoteResponse(Long voteId, String title, List<VoteOptionResponse> voteOptions,
                           List<SimpleCategoryResponse> categories) {

    public static VoteResponse from(final VoteCard voteCard) {
        return new VoteResponse(
                voteCard.getId(),
                voteCard.getTitle(),
                voteCard.getVoteOptions()
                        .stream()
                        .map(option -> new VoteOptionResponse(option.getId(), option.getContent()))
                        .toList(),
                SimpleCategoryResponse.toResponse(voteCard.getCategories())
        );
    }

    public static List<VoteResponse> from(final List<VoteCard> voteCards) {
        return voteCards.stream()
                .map(VoteResponse::from)
                .toList();
    }
}
