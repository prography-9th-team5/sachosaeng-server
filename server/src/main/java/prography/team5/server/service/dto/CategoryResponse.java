package prography.team5.server.service.dto;

import java.util.List;
import prography.team5.server.domain.category.Category;

public record CategoryResponse(Long categoryId, String name, String iconUrl, String backgroundColor, String textColor) {

    public static CategoryResponse toResponse(final Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCategoryDesign().getIconUrl(),
                category.getCategoryDesign().getBackgroundColor(),
                category.getCategoryDesign().getTextColor()
        );
    }

    public static List<CategoryResponse> toResponse(final List<Category> categories) {
        return categories.stream()
                .map(CategoryResponse::toResponse)
                .toList();
    }
}
