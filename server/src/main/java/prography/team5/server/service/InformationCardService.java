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
import prography.team5.server.service.dto.CardIdResponse;
import prography.team5.server.service.dto.CardRequest;
import prography.team5.server.service.dto.CardResponse;

@RequiredArgsConstructor
@Service
public class InformationCardService {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final InformationCardRepository informationCardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CardResponse findByCardId(final long cardId) {
        final InformationCard informationCard = informationCardRepository.findById(cardId)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_CARD_ID));
        return CardResponse.from(informationCard);
    }

    @Transactional
    public CardIdResponse add(final CardRequest cardRequest) {
        final List<Long> categoryIds = cardRequest.categoryIds();
        final List<Category> categories = categoryRepository.findAllByIdIn(categoryIds);
        final InformationCard informationCard = new InformationCard(cardRequest.title(), categories, cardRequest.content());
        final Long cardId = informationCardRepository.save(informationCard).getId();
        return new CardIdResponse(cardId);
    }

    @Transactional(readOnly = true)
    public List<CardResponse> findAll(final Long cursor, final Long categoryId) {
        if (Objects.isNull(categoryId)) {
            return findAll(cursor);
        }
        return findAllByCategoryId(cursor, categoryId);
    }

    private List<CardResponse> findAll(final Long cursor) {
        final PageRequest pageRequest = PageRequest.ofSize(DEFAULT_PAGE_SIZE);
        if (Objects.isNull(cursor)) {
            return CardResponse.from(informationCardRepository.findLatestCards(pageRequest).getContent());
        }
        return CardResponse.from(informationCardRepository.findBeforeCursor(cursor, pageRequest).getContent());
    }

    private List<CardResponse> findAllByCategoryId(final Long cursor, final long categoryId) {
        final PageRequest pageRequest = PageRequest.ofSize(DEFAULT_PAGE_SIZE);
        if (Objects.isNull(cursor)) {
            return CardResponse.from(informationCardRepository.findLatestCardsByCategoriesId(categoryId, pageRequest).getContent());
        }
        return CardResponse.from(
                informationCardRepository.findByCategoriesIdBeforeCursor(cursor, categoryId, pageRequest).getContent());
    }
}
