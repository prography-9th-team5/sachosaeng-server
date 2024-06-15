package prography.team5.server.service.dto;

import java.util.List;
import prography.team5.server.domain.category.Category;

public record CategoryWithThumbnailResponse(Long categoryId, String name, String thumbnail) {

    //todo: 썸네일 url Converter?
    public static List<CategoryWithThumbnailResponse> from(final List<Category> categories) {
        return categories.stream()
                .map(each -> new CategoryWithThumbnailResponse(each.getId(), each.getName(), each.getCategoryDesign().getThumbnail()))
                .toList();
    }
}
