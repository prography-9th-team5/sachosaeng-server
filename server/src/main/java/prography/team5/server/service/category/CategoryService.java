package prography.team5.server.service.category;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.category.Category;
import prography.team5.server.domain.category.CategoryRepository;
import prography.team5.server.exception.ErrorType;
import prography.team5.server.exception.SachosaengException;
import prography.team5.server.service.category.dto.CategoryRequest;
import prography.team5.server.service.category.dto.CategoryResponse;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        final List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .toList();
    }

    @Transactional
    public void add(final CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new SachosaengException(ErrorType.DUPLICATED_CATEGORY);
        }
        categoryRepository.save(new Category(categoryRequest.name()));
    }
}
