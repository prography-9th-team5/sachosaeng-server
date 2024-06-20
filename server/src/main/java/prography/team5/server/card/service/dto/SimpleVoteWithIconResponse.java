package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;

public record SimpleVoteWithIconResponse(Long voteId, String title, Integer voteCount, String iconUrl) {

    public static SimpleVoteWithIconResponse toResponse(final VoteCard vote) {
        return new SimpleVoteWithIconResponse(
                vote.getId(),
                vote.getTitle(),
                null,
                vote.getCategories().get(0).getCategoryDesign().getIconUrl()
        );
    }

    public static List<SimpleVoteWithIconResponse> toResponse(final List<VoteCard> votes) {
        return votes.stream()
                .map(SimpleVoteWithIconResponse::toResponse)
                .toList();
    }
}
