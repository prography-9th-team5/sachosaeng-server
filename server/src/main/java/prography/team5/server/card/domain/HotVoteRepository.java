package prography.team5.server.card.domain;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;

public interface HotVoteRepository {

    List<VoteCard> findHotVotesOfAllCategory(int size);

    List<VoteCard> findHotVotesOfCategory(int size, Long categoryId);
}
