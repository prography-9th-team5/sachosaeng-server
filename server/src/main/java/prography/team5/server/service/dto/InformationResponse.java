package prography.team5.server.service.dto;

import java.util.List;
import prography.team5.server.domain.card.InformationCard;

public record InformationResponse(Long informationId, String title, String content, List<SimpleCategoryResponse> categories) {

    public static InformationResponse from(final InformationCard informationCard) {
        return new InformationResponse(
                informationCard.getId(),
                informationCard.getTitle(),
                informationCard.getContent(),
                SimpleCategoryResponse.toSimpleCategoryResponseList(informationCard.getCategories())
        );
    }

    public static List<InformationResponse> from(final List<InformationCard> informationCards) {
        return informationCards.stream().map(InformationResponse::from).toList();
    }
}
