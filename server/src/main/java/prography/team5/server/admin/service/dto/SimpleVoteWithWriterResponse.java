package prography.team5.server.admin.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;

public record SimpleVoteWithWriterResponse(Long voteId, String title, Long userId, String adminName) {

    public static List<SimpleVoteWithWriterResponse> toResponse(final List<VoteCard> voteCards) {
        return voteCards.stream().map(
                each -> new SimpleVoteWithWriterResponse(
                        each.getId(),
                        each.getTitle(),
                        each.getWriterId(),
                        each.getAdminName()
                )
        ).toList();
    }
}
