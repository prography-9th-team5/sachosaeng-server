package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;


public record SimpleVoteResponse(Long voteId, String title, Integer voteCount) {

    public static SimpleVoteResponse toResponse(final VoteCard voteCard) {
        //todo: null 기준 판단
        return new SimpleVoteResponse(voteCard.getId(), voteCard.getTitle(), null);
    }

    public static List<SimpleVoteResponse> toResponse(final List<VoteCard> voteCards) {
        return voteCards.stream()
                .map(SimpleVoteResponse::toResponse)
                .toList();
    }
}
