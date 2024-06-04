package prography.team5.server.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.card.InformationCard;
import prography.team5.server.domain.card.InformationCardRepository;
import prography.team5.server.domain.category.Category;
import prography.team5.server.domain.category.CategoryRepository;
import prography.team5.server.exception.ErrorType;
import prography.team5.server.exception.SachosaengException;
import prography.team5.server.service.dto.InformationIdResponse;
import prography.team5.server.service.dto.InformationRequest;
import prography.team5.server.service.dto.InformationResponse;

@RequiredArgsConstructor
@Service
public class InformationService {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final InformationCardRepository informationCardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public InformationResponse findByInformationId(final long cardId) {
        final InformationCard informationCard = informationCardRepository.findById(cardId)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_INFORMATION_CARD_ID));
        return InformationResponse.from(informationCard);
    }

    @Transactional
    public InformationIdResponse create(final InformationRequest informationRequest) {
        final List<Long> categoryIds = informationRequest.categoryIds();
        final List<Category> categories = categoryRepository.findAllByIdIn(categoryIds);
        final InformationCard informationCard = new InformationCard(informationRequest.title(), categories, informationRequest.content());
        final Long cardId = informationCardRepository.save(informationCard).getId();
        return new InformationIdResponse(cardId);
    }

    @Transactional(readOnly = true)
    public List<InformationResponse> findAll(final Long cursor, final Long categoryId) {
        if (Objects.isNull(categoryId)) {
            return findAll(cursor);
        }
        return findAllByCategoryId(cursor, categoryId);
    }

    private List<InformationResponse> findAll(final Long cursor) {
        final PageRequest pageRequest = PageRequest.ofSize(DEFAULT_PAGE_SIZE);
        if (Objects.isNull(cursor)) {
            return InformationResponse.from(informationCardRepository.findLatestCards(pageRequest).getContent());
        }
        return InformationResponse.from(informationCardRepository.findBeforeCursor(cursor, pageRequest).getContent());
    }

    private List<InformationResponse> findAllByCategoryId(final Long cursor, final long categoryId) {
        final PageRequest pageRequest = PageRequest.ofSize(DEFAULT_PAGE_SIZE);
        if (Objects.isNull(cursor)) {
            return InformationResponse.from(informationCardRepository.findLatestCardsByCategoriesId(categoryId, pageRequest).getContent());
        }
        return InformationResponse.from(
                informationCardRepository.findByCategoriesIdBeforeCursor(cursor, categoryId, pageRequest).getContent());
    }
}
