package prography.team5.server.card.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.card.domain.InformationCard;
import prography.team5.server.card.repository.InformationCardRepository;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;
import prography.team5.server.card.service.dto.InformationIdResponse;
import prography.team5.server.card.service.dto.InformationRequest;
import prography.team5.server.card.service.dto.InformationResponse;

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
        final InformationCard informationCard = new InformationCard(informationRequest.title(), categories,
                informationRequest.content());
        final Long cardId = informationCardRepository.save(informationCard).getId();
        return new InformationIdResponse(cardId);
    }

    @Transactional(readOnly = true)
    public List<InformationResponse> findAll(final Long cursor, final Long categoryId, final Integer pageSize) {
        final PageRequest pageRequest = createPageRequest(pageSize);
        if (Objects.isNull(categoryId)) {
            return findAll(cursor, pageRequest);
        }
        return findAllByCategoryId(cursor, categoryId, pageRequest);
    }

    private PageRequest createPageRequest(final Integer pageSize) {
        if (Objects.isNull(pageSize)) {
            return PageRequest.ofSize(DEFAULT_PAGE_SIZE);
        }
        return PageRequest.ofSize(pageSize);
    }

    private List<InformationResponse> findAll(final Long cursor, final PageRequest pageRequest) {
        if (Objects.isNull(cursor)) {
            return InformationResponse.from(informationCardRepository.findLatestCards(pageRequest).getContent());
        }
        return InformationResponse.from(informationCardRepository.findBeforeCursor(cursor, pageRequest).getContent());
    }

    private List<InformationResponse> findAllByCategoryId(final Long cursor, final long categoryId,
                                                          final PageRequest pageRequest) {
        if (Objects.isNull(cursor)) {
            return InformationResponse.from(
                    informationCardRepository.findLatestCardsByCategoriesId(categoryId, pageRequest).getContent());
        }
        return InformationResponse.from(
                informationCardRepository.findByCategoriesIdBeforeCursor(cursor, categoryId, pageRequest).getContent());
    }
}
