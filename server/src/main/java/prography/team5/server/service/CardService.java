package prography.team5.server.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.card.Card;
import prography.team5.server.domain.card.CardRepository;
import prography.team5.server.domain.category.Category;
import prography.team5.server.domain.category.CategoryRepository;
import prography.team5.server.exception.ErrorType;
import prography.team5.server.exception.SachosaengException;
import prography.team5.server.service.dto.CardIdResponse;
import prography.team5.server.service.dto.CardRequest;
import prography.team5.server.service.dto.CardResponse;

@RequiredArgsConstructor
@Service
public class CardService {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final CardRepository cardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CardResponse findByCardId(final long cardId) {
        final Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_CARD_ID));
        return CardResponse.from(card);
    }

    @Transactional
    public CardIdResponse add(final CardRequest cardRequest) {
        final List<Long> categoryIds = cardRequest.categoryIds();
        final List<Category> categories = categoryRepository.findAllByIdIn(categoryIds);
        final Card card = new Card(cardRequest.title(), cardRequest.content(), categories);
        final Long cardId = cardRepository.save(card).getId();
        return new CardIdResponse(cardId);
    }

    @Transactional(readOnly = true)
    public List<CardResponse> findAll(final Long cursor, final Long categoryId) {
        if(Objects.isNull(categoryId)) {
            return findAll(cursor);
        }
        return null;
        //return findAllByCategoryId(cursor, categoryId);
    }

    private List<CardResponse> findAll(final Long cursor) {
        final PageRequest pageRequest = PageRequest.ofSize(DEFAULT_PAGE_SIZE);
        if(Objects.isNull(cursor)) {
            return CardResponse.from(cardRepository.findLatestCards(pageRequest).getContent());
        }
        return CardResponse.from(cardRepository.findCardsBeforeCursor(cursor, pageRequest).getContent());
    }

/*    //todo: 고치기
    private List<CardResponse> findAllByCategoryId(final Long cursor, final long categoryId) {
        final PageRequest pageRequest = PageRequest.ofSize(3);
        if(Objects.isNull(cursor)) {
            cardRepository.findLatestCardsByCategoriesId(pageRequest);
        }
        List<Card> cards = cardRepository.findByCategoriesId(categoryId);
        return CardResponse.from(cards);
    }*/
}
