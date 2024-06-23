package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;


public record SimpleVoteResponse(Long voteId, String title, Long participantCount) {

    public static SimpleVoteResponse toResponse(final VoteCard voteCard) {
        //todo: 투표수 아니고 참여자수
        return new SimpleVoteResponse(voteCard.getId(), voteCard.getTitle(), voteCard.getCount());
    }

    public static List<SimpleVoteResponse> toResponse(final List<VoteCard> voteCards) {
        return voteCards.stream()
                .map(SimpleVoteResponse::toResponse)
                .toList();
    }
}
