package prography.team5.server.admin.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.admin.service.dto.SimpleVoteWithWriterResponse;
import prography.team5.server.admin.service.dto.VoteWithAdminNameRequest;
import prography.team5.server.admin.service.dto.VoteWithFullCategoriesResponse;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.VoteCardRepository;
import prography.team5.server.card.service.dto.VoteIdResponse;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;

@RequiredArgsConstructor
@Service
public class VoteAdminService {

    private final VoteCardRepository voteCardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public VoteIdResponse create(final VoteWithAdminNameRequest request) {
        final List<Category> categories = categoryRepository.findAllByIdIn(request.categoryIds());
        final VoteCard voteCard = VoteCard.of(request.title(), categories, request.isMultipleChoiceAllowed(), request.adminName());
        voteCard.updateVoteOptions(request.voteOptions());
        return new VoteIdResponse(voteCardRepository.save(voteCard).getId());
    }

    @Transactional(readOnly = true)
    public List<SimpleVoteWithWriterResponse> findAll() {
        //final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.DESC, "id"));
        final List<VoteCard> all = voteCardRepository.findAll(Sort.by(Direction.DESC, "id"));
        return SimpleVoteWithWriterResponse.toResponse(all);
    }

    @Transactional(readOnly = true)
    public VoteWithFullCategoriesResponse findById(
            final Long voteId
    ) {
        final VoteCard voteCard = voteCardRepository.findById(voteId).orElseThrow();
        return VoteWithFullCategoriesResponse.toResponse(voteCard);
    }

    @Transactional
    public void modifyById(final Long voteId, final VoteWithAdminNameRequest request) {
        final VoteCard voteCard = voteCardRepository.findById(voteId).orElseThrow();
        final List<Category> categories = categoryRepository.findAllByIdIn(request.categoryIds());
        voteCard.updateAll(
                request.title(),
                categories,
                request.voteOptions(),
                request.isMultipleChoiceAllowed(),
                request.adminName()
        );
    }
}
