package prography.team5.server.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.category.BookmarkCategory;
import prography.team5.server.domain.category.BookmarkCategoryRepository;
import prography.team5.server.service.dto.CategoryResponse;

@RequiredArgsConstructor
@Service
public class BookmarkCategoryService {

    private final BookmarkCategoryRepository bookmarkCategoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllByUserId(final long userId) {
        List<BookmarkCategory> categories = bookmarkCategoryRepository.findAllByUserId(userId);
        return CategoryResponse.from(categories.stream().map(BookmarkCategory::getCategory).toList());
    }
}
