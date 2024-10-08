package prography.team5.server.admin.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.admin.service.dto.FullInformationResponse;
import prography.team5.server.card.domain.InformationCard;
import prography.team5.server.card.repository.InformationCardRepository;
import prography.team5.server.admin.service.dto.InformationCreationRequest;
import prography.team5.server.card.service.dto.InformationIdResponse;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;

@RequiredArgsConstructor
@Service
public class InformationAdminService {

    private final InformationCardRepository informationCardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public InformationIdResponse create(final InformationCreationRequest informationCreationRequest) {
        final List<Long> categoryIds = informationCreationRequest.categoryIds();
        final List<Category> categories = categoryRepository.findAllByIdIn(categoryIds);
        final InformationCard informationCard = new InformationCard(
                informationCreationRequest.title(),
                categories,
                informationCreationRequest.subtitle(),
                informationCreationRequest.content(),
                informationCreationRequest.referenceName(),
                informationCreationRequest.referenceUrl(),
                informationCreationRequest.adminName()
        );
        final Long cardId = informationCardRepository.save(informationCard).getId();
        return new InformationIdResponse(cardId);
    }

    @Transactional(readOnly = true)
    public FullInformationResponse findByInformationId(final long cardId) {
        final InformationCard informationCard = informationCardRepository.findById(cardId)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_INFORMATION_CARD_ID));
        return FullInformationResponse.from(informationCard);
    }

    @Transactional(readOnly = true)
    public List<FullInformationResponse> findAll() {
        final List<InformationCard> all = informationCardRepository.findAll(Sort.by(Direction.DESC, "id"));
        return FullInformationResponse.from(all);
    }

    @Transactional
    public void modifyById(final Long informationId, final InformationCreationRequest request) {
        final InformationCard informationCard = informationCardRepository.findById(informationId).orElseThrow();
        final List<Category> categories = categoryRepository.findAllByIdIn(request.categoryIds());
        informationCard.updateAll(
                request.title(),
                request.subtitle(),
                request.content(),
                categories,
                request.referenceName(),
                request.referenceUrl(),
                request.adminName()
        );
    }
}
