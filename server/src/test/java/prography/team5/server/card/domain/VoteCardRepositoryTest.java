package prography.team5.server.card.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;

@DataJpaTest
class VoteCardRepositoryTest {

    @Autowired
    private VoteCardRepository voteCardRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void 투표수가_가장_적은_투표를_하나_가져온다() {
        // given
        final Category category = categoryRepository.save(new Category("카테고리"));

        final VoteCard vote1 = VoteCard.of("제목", List.of(category), false, "관리자");
        voteCardRepository.save(vote1);

        final VoteCard vote2 = VoteCard.of("제목2", List.of(category), false, "관리자");
        vote2.updateVoteOptions(List.of("선지1", "선지2"));
        voteCardRepository.save(vote2);
        final Long id = vote2.getVoteOptions().get(0).getId();
        assertThat(id).isNotNull();
        vote2.chooseVoteOption(List.of(id));

        // when
        final List<VoteCard> withFewestParticipants = voteCardRepository.findWithFewestParticipants(
                PageRequest.ofSize(1)
        );

        // then
        assertThat(withFewestParticipants).hasSize(1);
        assertThat(withFewestParticipants.get(0).getTitle()).isEqualTo(vote1.getTitle());
    }
}
