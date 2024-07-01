package prography.team5.server.category.service.dto;

import java.util.List;
import prography.team5.server.category.domain.Category;

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

    public static CategoryResponse toResponseWith18px(final Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCategoryDesign().getIconUrl18px(),
                category.getCategoryDesign().getBackgroundColor(),
                category.getCategoryDesign().getTextColor()
        );
    }
}
