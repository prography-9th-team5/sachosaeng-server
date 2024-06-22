package prography.team5.server.admin.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.admin.service.dto.CategoryUpdateRequest;
import prography.team5.server.admin.service.dto.CategoryWithUserTypesResponse;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;
import prography.team5.server.category.service.dto.CategoryIdResponse;
import prography.team5.server.category.service.dto.CategoryRequest;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;
import prography.team5.server.user.domain.UserType;

@Service
@RequiredArgsConstructor
public class CategoryAdminService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryIdResponse create(final CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new SachosaengException(ErrorType.DUPLICATED_CATEGORY);
        }
        final Category category = new Category(categoryRequest.name());
        return new CategoryIdResponse(categoryRepository.save(category).getId());
    }

    @Transactional(readOnly = true)
    public List<CategoryWithUserTypesResponse> findAll() {
        final List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return CategoryWithUserTypesResponse.toResponse(categories);
    }

    @Transactional
    public void update(final Long categoryId, final CategoryUpdateRequest categoryUpdateRequest) {
        final Category category = categoryRepository.findById(categoryId).orElseThrow();//todo:
        category.update(
                categoryUpdateRequest.name(),
                categoryUpdateRequest.userTypes().stream().map(UserType::convert).toList()
        );
    }
}
