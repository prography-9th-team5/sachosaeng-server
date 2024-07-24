package prography.team5.server.admin.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.service.dto.SimpleCategoryResponse;
import prography.team5.server.card.service.dto.VoteOptionResponse;

public record VoteWithFullCategoriesResponse(
        Long voteId,
        long participantCount,
        List<SimpleCategoryResponse> categories,
        String title,
        Boolean isMultipleChoiceAllowed,
        List<VoteOptionResponse> voteOptions,
        String adminName

) {

    public static VoteWithFullCategoriesResponse toResponse(final VoteCard voteCard) {
        return new VoteWithFullCategoriesResponse(
                voteCard.getId(),
                voteCard.getParticipantCount(),
                SimpleCategoryResponse.toResponse(voteCard.getCategories()),
                voteCard.getTitle(),
                voteCard.isMultipleChoiceAllowed(),
                VoteOptionResponse.toResponse(voteCard.getVoteOptions()),
                voteCard.getAdminName()
        );
    }
}
