package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;


public record SimpleVoteResponse(Long voteId, String title, Long participantCount, boolean isVoted) {

    public static SimpleVoteResponse toResponse(final VoteCard voteCard) {
        // todo: 투표 여부를 일단은 하드코딩!
        return new SimpleVoteResponse(voteCard.getId(), voteCard.getTitle(), voteCard.getParticipantCount(), false);
    }

    public static List<SimpleVoteResponse> toResponse(final List<VoteCard> voteCards) {
        return voteCards.stream()
                .map(SimpleVoteResponse::toResponse)
                .toList();
    }
}
