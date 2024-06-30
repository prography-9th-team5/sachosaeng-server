package prography.team5.server.card.service.dto;

import prography.team5.server.card.domain.VoteCard;

public record DailyVoteResponse(Long voteId, String title, String description) {

    public static DailyVoteResponse toResponse(final VoteCard voteCard) {
        return new DailyVoteResponse(
                voteCard.getId(),
                voteCard.getTitle(),
                "*판단에 도움을 주기 위한 참고 자료로 활용해 주세요"
        );
    }
}
