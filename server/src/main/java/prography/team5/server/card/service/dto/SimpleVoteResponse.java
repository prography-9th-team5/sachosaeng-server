package prography.team5.server.card.service.dto;

import java.util.List;
import java.util.Map;
import prography.team5.server.card.domain.VoteCard;


public record SimpleVoteResponse(Long voteId, String title, Long participantCount, boolean isVoted) {

    public static SimpleVoteResponse toResponse(final VoteCard voteCard, final boolean isVoted) {
        return new SimpleVoteResponse(
                voteCard.getId(),
                voteCard.getTitle(),
                voteCard.getParticipantCount(),
                isVoted
        );
    }

    public static List<SimpleVoteResponse> toResponse(final List<VoteCard> voteCards, final Map<Long, Boolean> isVotedAnalysis) {
        // orDefault는 관리자페이지를 위한 응급처치임
        return voteCards.stream()
                .map(each -> toResponse(each, isVotedAnalysis.getOrDefault(each.getId(), false)))
                .toList();
    }

    public static SimpleVoteResponse toHotVoteResponse(final VoteCard voteCard, final boolean isVoted) {
        return new SimpleVoteResponse(voteCard.getId(), voteCard.getTitle(), voteCard.getHotParticipantCount(), isVoted);
    }

    public static List<SimpleVoteResponse> toHotVoteResponse(final List<VoteCard> votes, final Map<Long, Boolean> isVotedAnalysis) {
        return votes.stream()
                .map(each -> toHotVoteResponse(each, isVotedAnalysis.getOrDefault(each.getId(), false)))
                .toList();
    }
}
