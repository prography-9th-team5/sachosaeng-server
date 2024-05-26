package prography.team5.server.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
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
}
