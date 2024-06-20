package prography.team5.server.service.dto;

import java.util.List;
import lombok.Data;
import prography.team5.server.domain.category.Category;

@Data
public class BaseCategoryResponse {

    private final Long categoryId;
    private final String name;

    public static List<BaseCategoryResponse> from(final List<Category> categories) {
        return categories.stream()
                .map(each -> new BaseCategoryResponse(each.getId(), each.getName()))
                .toList();
    }
}
