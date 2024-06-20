package prography.team5.server.service.dto;

import java.util.List;
import lombok.Getter;
import prography.team5.server.domain.category.Category;

@Getter
public class SimpleCategoryResponse {

    private final Long categoryId;
    private final String name;

    protected SimpleCategoryResponse(final Long categoryId, final String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public static List<SimpleCategoryResponse> toSimpleCategoryResponseList(final List<Category> categories) {
        return categories.stream()
                .map(each -> new SimpleCategoryResponse(each.getId(), each.getName()))
                .toList();
    }
}
