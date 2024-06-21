package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;

public record VoteResponse(Long voteId, String title, List<VoteOptionResponse> voteOptions,
                           List<SimpleCategoryResponse> categories) {

    public static VoteResponse toResponse(final VoteCard voteCard) {
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

    public static List<VoteResponse> toResponse(final List<VoteCard> voteCards) {
        return voteCards.stream()
                .map(VoteResponse::toResponse)
                .toList();
    }
}
