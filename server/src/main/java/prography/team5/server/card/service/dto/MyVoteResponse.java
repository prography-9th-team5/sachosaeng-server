package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.domain.VoteOption;
import prography.team5.server.category.service.dto.CategoryResponse;

public record MyVoteResponse(
        Long voteId,
        String status,
        String title,
        boolean isMultipleChoiceAllowed,
        List<String> voteOptions,
        List<CategoryResponse> categories
) {

        public static MyVoteResponse toResponse(final VoteCard voteCard) {
                return new MyVoteResponse(
                        voteCard.getId(),
                        voteCard.getApprovalStatus().name(),
                        voteCard.getTitle(),
                        voteCard.isMultipleChoiceAllowed(),
                        voteCard.getVoteOptions().stream().map(VoteOption::getContent).toList(),
                        CategoryResponse.toResponse(voteCard.getCategories())
                );
        }
}
