package prography.team5.server.service.dto;

import lombok.Getter;
import prography.team5.server.domain.category.Category;

@Getter
public class SimpleCategoryWithTextColorResponse extends SimpleCategoryResponse {

    private final String textColor;

    public SimpleCategoryWithTextColorResponse(final Long categoryId, final String name, final String textColor) {
        super(categoryId, name);
        this.textColor = textColor;
    }

    public static SimpleCategoryWithTextColorResponse toResponse(final Category category) {
        return new SimpleCategoryWithTextColorResponse(
                category.getId(),
                category.getName(),
                category.getCategoryDesign().getTextColor()
        );
    }
}
