package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.service.dto.CategoryResponse;

public record VoteResponse(
        Long voteId,
        boolean isVoted,
        CategoryResponse category,
        String title,
        Long count,
        List<VoteOptionResponse> voteOptions,
        String description
) {

    //todo: 연관 콘텐츠
    //todo: 문구

    public static VoteResponse toResponse(final Category category, final boolean isVoted, final VoteCard voteCard) {
        return new VoteResponse(
                voteCard.getId(),
                isVoted,
                CategoryResponse.toResponse(category),
                voteCard.getTitle(),
                voteCard.getCount(),
                voteCard.getVoteOptions()
                        .stream()
                        .map(option -> new VoteOptionResponse(option.getId(), option.getContent(), option.getCount()))
                        .toList(),
                "[임시문구] 나와 동년배는 ~~를 많이 선택했어요!"

        );
    }

/*    public static VoteResponse toResponse(final VoteCard voteCard) {
        return new VoteResponse(
                CategoryResponse.toResponse(voteCard.getCategories().get(0)),
                voteCard.getId(),
                voteCard.getTitle(),
                voteCard.getVoteOptions()
                        .stream()
                        .map(option -> new VoteOptionResponse(option.getId(), option.getContent()))
                        .toList(),
                SimpleCategoryResponse.toResponse(voteCard.getCategories())
        );
    }

    public static List<VoteResponse> toResponse(final List<VoteCard> voteCards) {
        return voteCards.stream()
                .map(VoteResponse::toResponse)
                .toList();
    }*/
}
