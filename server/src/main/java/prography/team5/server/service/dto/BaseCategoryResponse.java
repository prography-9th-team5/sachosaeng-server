package prography.team5.server.service.dto;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import prography.team5.server.domain.category.Category;

@Getter
public class BaseCategoryResponse {

    private final Long categoryId;
    private final String name;

    protected BaseCategoryResponse(final Long categoryId, final String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public static List<BaseCategoryResponse> toBaseCategoryResponseList(final List<Category> categories) {
        return categories.stream()
                .map(each -> new BaseCategoryResponse(each.getId(), each.getName()))
                .toList();
    }
}
