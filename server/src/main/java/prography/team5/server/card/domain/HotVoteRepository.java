package prography.team5.server.card.domain;

import java.util.List;

public interface HotVoteRepository {

    List<VoteCard> findHotVotes(int size);

    List<VoteCard> findHotVotesOfCategory(int size, Long categoryId);
}
