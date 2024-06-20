package prography.team5.server.service.dto;

import java.util.List;
import lombok.Getter;
import prography.team5.server.domain.card.VoteCard;

@Getter
public class BaseVoteResponse {

    private final Long voteId;
    private final String title;
    private final Integer voteCount;

    protected BaseVoteResponse(final Long voteId, final String title, final Integer voteCount) {
        this.voteId = voteId;
        this.title = title;
        this.voteCount = voteCount;
    }

    public static BaseVoteResponse toResponse(final VoteCard voteCard) {
        //todo: null 기준 판단
        return new BaseVoteResponse(voteCard.getId(), voteCard.getTitle(), null);
    }

    public static List<BaseVoteResponse> toResponse(final List<VoteCard> voteCards) {
        return voteCards.stream()
                .map(BaseVoteResponse::toResponse)
                .toList();
    }
}
