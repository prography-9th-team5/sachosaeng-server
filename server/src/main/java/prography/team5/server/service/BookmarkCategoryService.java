package prography.team5.server.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.category.BookmarkCategory;
import prography.team5.server.domain.category.BookmarkCategoryRepository;
import prography.team5.server.domain.category.Category;
import prography.team5.server.domain.category.CategoryRepository;
import prography.team5.server.service.dto.BookmarkCategoryRequest;
import prography.team5.server.service.dto.CategoryResponse;

@RequiredArgsConstructor
@Service
public class BookmarkCategoryService {

    private final BookmarkCategoryRepository bookmarkCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllByUserId(final long userId) {
        List<BookmarkCategory> categories = bookmarkCategoryRepository.findAllByUserId(userId);
        return CategoryResponse.from(categories.stream().map(BookmarkCategory::getCategory).toList());
    }

    //todo : 리팩터링
    @Transactional
    public void updateAllByUserId(final long userId, final BookmarkCategoryRequest bookmarkCategoryRequest) {
        final List<Category> categories = categoryRepository.findAllByIdIn(bookmarkCategoryRequest.categoryIds());
        final List<BookmarkCategory> bookmarkCategories = categories.stream()
                .map(each -> new BookmarkCategory(userId, each))
                .toList();
        bookmarkCategoryRepository.deleteAllByUserId(userId);
        bookmarkCategoryRepository.saveAll(bookmarkCategories);
    }
}
