package prography.team5.server.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.card.VoteCard;
import prography.team5.server.domain.card.VoteCardRepository;
import prography.team5.server.domain.card.VoteOption;
import prography.team5.server.domain.category.Category;
import prography.team5.server.domain.category.CategoryRepository;
import prography.team5.server.service.dto.CategoryResponse;
import prography.team5.server.service.dto.VoteIdResponse;
import prography.team5.server.service.dto.VoteOptionResponse;
import prography.team5.server.service.dto.VoteRequest;
import prography.team5.server.service.dto.VoteResponse;

@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteCardRepository voteCardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public VoteIdResponse create(final VoteRequest voteRequest, final long userId) {
        final List<Category> categories = categoryRepository.findAllByIdIn(voteRequest.categoryIds());
        final VoteCard voteCard = new VoteCard(voteRequest.title(), categories, userId);
        voteRequest.voteOptions()
                .forEach(voteCard::addVoteOption);
        return new VoteIdResponse(voteCardRepository.save(voteCard).getId());
    }

    @Transactional(readOnly = true)
    public VoteResponse findByVoteId(final long voteId) {
        final VoteCard voteCard = voteCardRepository.findById(voteId).orElseThrow();
        return new VoteResponse(
                voteCard.getId(),
                voteCard.getTitle(),
                voteCard.getVoteOptions()
                        .stream()
                        .map(option -> new VoteOptionResponse(option.getId(), option.getContent()))
                        .toList(),
                CategoryResponse.from(voteCard.getCategories())
        );
    }
}
