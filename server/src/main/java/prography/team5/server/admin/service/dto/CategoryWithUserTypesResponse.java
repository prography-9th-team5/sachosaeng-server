package prography.team5.server.admin.service.dto;

import java.util.List;
import prography.team5.server.category.domain.Category;
import prography.team5.server.user.domain.UserType;

public record CategoryWithUserTypesResponse(
        Long categoryId,
        String name,
        String iconUrl,
        String backgroundColor,
        String textColor,
        List<String> userTypes
) {

    public static CategoryWithUserTypesResponse toResponse(final Category category) {
        return new CategoryWithUserTypesResponse(
                category.getId(),
                category.getName(),
                category.getCategoryDesign().getIconUrl(),
                category.getCategoryDesign().getBackgroundColor(),
                category.getCategoryDesign().getTextColor(),
                category.getUserTypes().stream().map(UserType::getDescription).toList()
        );
    }

    public static List<CategoryWithUserTypesResponse> toResponse(final List<Category> categories) {
        return categories.stream()
                .map(CategoryWithUserTypesResponse::toResponse)
                .toList();
    }
}
