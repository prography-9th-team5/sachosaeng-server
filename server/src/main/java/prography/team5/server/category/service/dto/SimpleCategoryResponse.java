package prography.team5.server.category.service.dto;

import java.util.List;
import prography.team5.server.category.domain.Category;

public record SimpleCategoryResponse(Long categoryId, String name) {

    public static List<SimpleCategoryResponse> toResponse(final List<Category> categories) {
        return categories.stream()
                .map(each -> new SimpleCategoryResponse(each.getId(), each.getName()))
                .toList();
    }
}
