package prography.team5.server.card.service;

import io.jsonwebtoken.lang.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.bookmark.domain.InformationCardBookmarkRepository;
import prography.team5.server.card.domain.InformationCard;
import prography.team5.server.card.repository.InformationCardRepository;
import prography.team5.server.card.service.dto.InformationResponse;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;

@RequiredArgsConstructor
@Service
public class InformationService {

    private final InformationCardRepository informationCardRepository;
    private final CategoryRepository categoryRepository;
    private final InformationCardBookmarkRepository informationCardBookmarkRepository;

    @Transactional(readOnly = true)
    public InformationResponse find(final Long userId, final long informationId, final Long categoryId) {
        final InformationCard informationCard = informationCardRepository.findById(informationId)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_INFORMATION_CARD_ID));
        Category category = getCategory(categoryId, informationCard);
        if(Objects.isEmpty(userId)) {
            return InformationResponse.from(informationCard, category, false);
        }
        final boolean isBookmarked = informationCardBookmarkRepository.existsByInformationCardAndUserId(informationCard, userId);
        return InformationResponse.from(informationCard, category, isBookmarked);
    }

    private Category getCategory(final Long categoryId, final InformationCard informationCard) {
        if(categoryId == null) {
            return informationCard.getCategories().get(0);
        }
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_CATEGORY));
        informationCard.checkCategory(category);
        return category;
    }
}
