package prography.team5.server.category.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;
import prography.team5.server.category.service.dto.CategoryIdResponse;
import prography.team5.server.category.service.dto.CategoryRequest;
import prography.team5.server.category.service.dto.CategoryResponse;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        final List<Category> categories = categoryRepository.findAll();
        return CategoryResponse.toResponse(categories);
    }

    @Transactional
    public CategoryIdResponse add(final CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new SachosaengException(ErrorType.DUPLICATED_CATEGORY);
        }
        return new CategoryIdResponse(categoryRepository.save(new Category(categoryRequest.name())).getId());
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(final long categoryId) {
        final Category category = categoryRepository.findById(categoryId).orElseThrow();//todo: 예외처리
        return CategoryResponse.toResponse(category);
    }
}
