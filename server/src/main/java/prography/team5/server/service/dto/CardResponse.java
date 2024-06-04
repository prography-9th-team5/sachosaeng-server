package prography.team5.server.service.dto;

import java.util.List;
import prography.team5.server.domain.card.InformationCard;

public record CardResponse(Long cardId, String title, String content, List<CategoryResponse> categories) {

    public static CardResponse from(final InformationCard informationCard) {
        return new CardResponse(
                informationCard.getId(),
                informationCard.getTitle(),
                informationCard.getContent(),
                CategoryResponse.from(informationCard.getCategories())
        );
    }

    public static List<CardResponse> from(final List<InformationCard> informationCards) {
        return informationCards.stream().map(CardResponse::from).toList();
    }
}
