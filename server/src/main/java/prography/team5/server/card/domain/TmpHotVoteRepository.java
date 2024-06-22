package prography.team5.server.card.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TmpHotVoteRepository implements HotVoteRepository {

    private final VoteCardRepository voteCardRepository;
    @Override
    public List<VoteCard> findHotVotesOfAllCategory(final int size) {
        return voteCardRepository.findLatestCards(
                PageRequest.ofSize(size)
        ).getContent();
    }

    @Override
    public List<VoteCard> findHotVotesOfCategory(final int size, final Long categoryId) {
        return voteCardRepository.findLatestCardsByCategoriesId(categoryId,
                PageRequest.ofSize(size)
        ).getContent();
    }
}
