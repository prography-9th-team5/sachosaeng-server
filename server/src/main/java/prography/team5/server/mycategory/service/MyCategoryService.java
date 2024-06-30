package prography.team5.server.mycategory.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.card.repository.InformationCardRepository;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;
import prography.team5.server.mycategory.domain.MyCategory;
import prography.team5.server.mycategory.domain.MyCategoryRepository;
import prography.team5.server.category.service.dto.CategoryResponse;
import prography.team5.server.mycategory.service.dto.MyCategoryRequest;

@RequiredArgsConstructor
@Service
public class MyCategoryService {

    private final MyCategoryRepository myCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final InformationCardRepository informationCardRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllByUserId(final long userId) {
        List<MyCategory> categories = myCategoryRepository.findAllByUserId(userId);
        return CategoryResponse.toResponse(categories.stream().map(MyCategory::getCategory).toList());
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

/*    @Transactional(readOnly = true)
    public List<InformationResponse> findAllInformationByUserId(final Long userId, final Long cursor) {
        final List<Long> categoryIds = myCategoryRepository.findAllByUserId(userId)
                .stream()
                .map(each -> each.getCategory().getId())
                .toList();

        final PageRequest pageRequest = PageRequest.ofSize(DEFAULT_PAGE_SIZE);
        if(Objects.isNull(cursor)) {
            return InformationResponse.from(
                    informationCardRepository.findLatestCardsByCategoriesIdIn(categoryIds, pageRequest).getContent());
        }
        return InformationResponse.from(
                informationCardRepository.findByCategoriesIdInBeforeCursor(cursor, categoryIds, pageRequest).getContent());
    }*/
}
