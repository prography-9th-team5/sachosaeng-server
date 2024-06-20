package prography.team5.server.service.dto;

import java.util.List;
import prography.team5.server.domain.category.Category;

public record SimpleCategoryResponse(Long categoryId, String name) {

    public static List<SimpleCategoryResponse> toResponse(final List<Category> categories) {
        return categories.stream()
                .map(each -> new SimpleCategoryResponse(each.getId(), each.getName()))
                .toList();
    }
}
