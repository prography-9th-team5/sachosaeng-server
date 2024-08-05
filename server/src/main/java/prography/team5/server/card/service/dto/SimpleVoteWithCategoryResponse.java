package prography.team5.server.card.service.dto;

import java.util.List;
import java.util.Map;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.service.dto.CategoryResponse;

public record SimpleVoteWithCategoryResponse(Long voteId, String title, Long participantCount, boolean isVoted, CategoryResponse category, boolean isClosed) {

    public static SimpleVoteWithCategoryResponse toHotVoteResponse(final VoteCard vote, final boolean isVoted) {
        final Category category = vote.getCategories().get(0);
        return new SimpleVoteWithCategoryResponse(
                vote.getId(),
                vote.getTitle(),
                vote.getHotParticipantCount(),
                isVoted,
                CategoryResponse.toResponseWith18px(category),
                vote.isClosed()
        );
    }

    public static List<SimpleVoteWithCategoryResponse> toHotVoteResponse(final List<VoteCard> votes, final Map<Long, Boolean> isVotedAnalysis) {
        return votes.stream()
                .map(each -> toHotVoteResponse(each, isVotedAnalysis.getOrDefault(each.getId(), false)))
                .toList();
    }

    public static SimpleVoteWithCategoryResponse toResponseWith18px(final VoteCard vote, final boolean isVoted) {
        final Category category = vote.getCategories().get(0);
        return new SimpleVoteWithCategoryResponse(
                vote.getId(),
                vote.getTitle(),
                vote.getParticipantCount(),
                isVoted,
                CategoryResponse.toResponseWith18px(category),
                vote.isClosed()
        );
    }
}
