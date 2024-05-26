package prography.team5.server.service.dto;

import java.util.List;
import prography.team5.server.domain.card.Card;

public record CardResponse(Long cardId, String title, String content, List<CategoryResponse> categories) {

    public static CardResponse from(final Card card) {
        return new CardResponse(
                card.getId(),
                card.getTitle(),
                card.getContent(),
                CategoryResponse.from(card.getCategories())
        );
    }
}
