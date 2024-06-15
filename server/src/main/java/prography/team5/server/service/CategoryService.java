package prography.team5.server.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.category.Category;
import prography.team5.server.domain.category.CategoryRepository;
import prography.team5.server.exception.ErrorType;
import prography.team5.server.exception.SachosaengException;
import prography.team5.server.service.dto.CategoryIdResponse;
import prography.team5.server.service.dto.CategoryRequest;
import prography.team5.server.service.dto.CategoryWithThumbnailResponse;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryWithThumbnailResponse> findAll() {
        final List<Category> categories = categoryRepository.findAll();
        return CategoryWithThumbnailResponse.from(categories);
    }

    @Transactional
    public CategoryIdResponse add(final CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new SachosaengException(ErrorType.DUPLICATED_CATEGORY);
        }
        return new CategoryIdResponse(categoryRepository.save(new Category(categoryRequest.name())).getId());
    }
}
