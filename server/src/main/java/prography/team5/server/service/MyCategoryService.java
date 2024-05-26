package prography.team5.server.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.card.CardRepository;
import prography.team5.server.domain.category.MyCategory;
import prography.team5.server.domain.category.MyCategoryRepository;
import prography.team5.server.domain.category.Category;
import prography.team5.server.domain.category.CategoryRepository;
import prography.team5.server.service.dto.MyCategoryRequest;
import prography.team5.server.service.dto.CardResponse;
import prography.team5.server.service.dto.CategoryResponse;

@RequiredArgsConstructor
@Service
public class MyCategoryService {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final MyCategoryRepository myCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final CardRepository cardRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllByUserId(final long userId) {
        List<MyCategory> categories = myCategoryRepository.findAllByUserId(userId);
        return CategoryResponse.from(categories.stream().map(MyCategory::getCategory).toList());
    }

    //todo : 리팩터링
    @Transactional
    public void updateAllByUserId(final long userId, final MyCategoryRequest myCategoryRequest) {
        final List<Category> categories = categoryRepository.findAllByIdIn(myCategoryRequest.categoryIds());
        final List<MyCategory> bookmarkCategories = categories.stream()
                .map(each -> new MyCategory(userId, each))
                .toList();
        myCategoryRepository.deleteAllByUserId(userId);
        myCategoryRepository.saveAll(bookmarkCategories);
    }

    @Transactional(readOnly = true)
    public List<CardResponse> findAllCardsByUserId(final Long userId, final Long cursor) {
        final List<Long> categoryIds = myCategoryRepository.findAllByUserId(userId)
                .stream()
                .map(each -> each.getCategory().getId())
                .toList();

        final PageRequest pageRequest = PageRequest.ofSize(DEFAULT_PAGE_SIZE);
        if(Objects.isNull(cursor)) {
            return CardResponse.from(cardRepository.findLatestCardsByCategoriesIdIn(categoryIds, pageRequest).getContent());
        }
        return CardResponse.from(cardRepository.findByCategoriesIdInBeforeCursor(cursor, categoryIds, pageRequest).getContent());
    }
}