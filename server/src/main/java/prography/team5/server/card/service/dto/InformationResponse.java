package prography.team5.server.card.service.dto;

import prography.team5.server.card.domain.InformationCard;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.service.dto.CategoryResponse;

public record InformationResponse(
        Long informationId,
        String title,
        String subtitle,
        String content,
        CategoryResponse category,
        String referenceName
) {

    public static InformationResponse from(final InformationCard informationCard, final Category category) {
        return new InformationResponse(
                informationCard.getId(),
                informationCard.getTitle(),
                informationCard.getSubtitle(),
                informationCard.getContent(),
                CategoryResponse.toResponse(category),
                informationCard.getReferenceName()
        );
    }
}
