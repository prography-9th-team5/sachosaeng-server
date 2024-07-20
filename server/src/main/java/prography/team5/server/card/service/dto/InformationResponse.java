package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.InformationCard;

public record InformationResponse(Long informationId, String title, String content, List<SimpleCategoryResponse> categories, String referenceName) {

    public static InformationResponse from(final InformationCard informationCard) {
        return new InformationResponse(
                informationCard.getId(),
                informationCard.getTitle(),
                informationCard.getContent(),
                SimpleCategoryResponse.toResponse(informationCard.getCategories()),
                informationCard.getReferenceName()
        );
    }

    public static List<InformationResponse> from(final List<InformationCard> informationCards) {
        return informationCards.stream().map(InformationResponse::from).toList();
    }
}
