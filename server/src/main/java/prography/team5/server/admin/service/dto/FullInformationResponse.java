package prography.team5.server.admin.service.dto;

import java.util.List;
import prography.team5.server.card.domain.InformationCard;
import prography.team5.server.card.service.dto.SimpleCategoryResponse;

public record FullInformationResponse(Long informationId, String title, String subtitle, String content, List<SimpleCategoryResponse> categories, String referenceName, String referenceUrl, String adminName) {

    public static FullInformationResponse from(final InformationCard informationCard) {
        return new FullInformationResponse(
                informationCard.getId(),
                informationCard.getTitle(),
                informationCard.getSubtitle(),
                informationCard.getContent(),
                SimpleCategoryResponse.toResponse(informationCard.getCategories()),
                informationCard.getReferenceName(),
                informationCard.getReferenceUrl(),
                informationCard.getAdminName()
        );
    }

    public static List<FullInformationResponse> from(final List<InformationCard> informationCards) {
        return informationCards.stream().map(FullInformationResponse::from).toList();
    }
}
