package prography.team5.server.admin.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.Category;

public record SimpleVoteWithWriterResponse(Long voteId, String title, Long userId, String adminName, List<Long> categoryIds) {

    public static List<SimpleVoteWithWriterResponse> toResponse(final List<VoteCard> voteCards) {
        return voteCards.stream().map(
                each -> new SimpleVoteWithWriterResponse(
                        each.getId(),
                        each.getTitle(),
                        each.getWriterId(),
                        each.getAdminName(),
                        each.getCategories().stream().map(Category::getId).toList()
                )
        ).toList();
    }
}
