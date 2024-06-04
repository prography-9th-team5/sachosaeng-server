package prography.team5.server.service.dto;

import java.util.List;
import prography.team5.server.domain.category.Category;

public record CategoryResponse(Long categoryId, String name) {

    public static List<CategoryResponse> from(final List<Category> categories) {
        return categories.stream()
                .map(each -> new CategoryResponse(each.getId(), each.getName()))
                .toList();
    }
}
