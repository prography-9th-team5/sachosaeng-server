package prography.team5.server.service.dto;

import java.util.List;
import prography.team5.server.domain.card.VoteCard;

public record VoteResponse(Long voteId, String title, List<VoteOptionResponse> voteOptions,
                           List<BaseCategoryResponse> categories) {

    public static VoteResponse from(final VoteCard voteCard) {
        return new VoteResponse(
                voteCard.getId(),
                voteCard.getTitle(),
                voteCard.getVoteOptions()
                        .stream()
                        .map(option -> new VoteOptionResponse(option.getId(), option.getContent()))
                        .toList(),
                BaseCategoryResponse.from(voteCard.getCategories())
        );
    }

    public static List<VoteResponse> from(final List<VoteCard> voteCards) {
        return voteCards.stream()
                .map(VoteResponse::from)
                .toList();
    }
}
