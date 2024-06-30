package prography.team5.server.card.repository;

import java.util.List;
import prography.team5.server.card.domain.VoteCard;

public interface HotVoteRepository {

    List<VoteCard> findHotVotes(int size);

    List<VoteCard> findHotVotesOfCategory(int size, Long categoryId);
}
