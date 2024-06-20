package prography.team5.server.service.dto;

import java.util.List;
import lombok.Getter;
import prography.team5.server.domain.category.Category;

@Getter
public class CategoryResponse extends BaseCategoryResponse {

    private final String iconUrl;
    private final String backgroundColor;
    private final String textColor;

    private CategoryResponse(
            final Long categoryId,
            final String name,
            final String iconUrl,
            final String backgroundColor,
            final String textColor
    ) {
        super(categoryId, name);
        this.iconUrl = iconUrl;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

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
                .map(each -> new CategoryResponse(
                        each.getId(),
                        each.getName(),
                        each.getCategoryDesign().getIconUrl(),
                        each.getCategoryDesign().getBackgroundColor(),
                        each.getCategoryDesign().getTextColor()
                ))
                .toList();
    }
}
