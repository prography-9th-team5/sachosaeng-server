package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.service.dto.CategoryResponse;

public record VoteResponse(
        Long voteId,
        boolean isClosed,
        boolean isVoted,
        List<Long> chosenVoteOptionId,
        CategoryResponse category,
        String title,
        Long participantCount,
        List<VoteOptionResponse> voteOptions,
        String description
) {

    //todo: 연관 콘텐츠
    //todo: 문구

    public static VoteResponse toResponseWith32px(final Category category, final boolean isVoted, final List<Long> voteOptionId, final VoteCard voteCard) {
        return new VoteResponse(
                voteCard.getId(),
                voteCard.isClosed(),
                isVoted,
                voteOptionId,
                CategoryResponse.toResponseWith32px(category),
                voteCard.getTitle(),
                voteCard.getParticipantCount(),
                voteCard.getVoteOptions()
                        .stream()
                        .map(option -> new VoteOptionResponse(option.getId(), option.getContent(), option.getCount()))
                        .toList(),
                "[임시문구] 나와 동년배는 ~~를 많이 선택했어요!"

        );
    }
}
