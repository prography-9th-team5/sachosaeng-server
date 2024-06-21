package prography.team5.server.card.service.dto;

import prography.team5.server.category.domain.Category;

public record SimpleCategoryWithTextColorResponse(Long categoryId, String name, String textColor) {

    public static SimpleCategoryWithTextColorResponse toResponse(final Category category) {
        return new SimpleCategoryWithTextColorResponse(
                category.getId(),
                category.getName(),
                category.getCategoryDesign().getTextColor()
        );
    }
}