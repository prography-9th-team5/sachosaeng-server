package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;

public record MyVotePreviewResponse(Long voteId, String title, String status) {

    public static List<MyVotePreviewResponse> toResponse(final List<VoteCard> votes) {
        return votes.stream().map(each-> new MyVotePreviewResponse(each.getId(), each.getTitle(), each.getApprovalStatus().name())).toList();
    }
}
