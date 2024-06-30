package prography.team5.server.card.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import prography.team5.server.card.domain.UserVoteOption;
import prography.team5.server.card.repository.UserVoteOptionRepository;

@DataJpaTest
class UserVoteOptionRepositoryTest {

    @Autowired
    private UserVoteOptionRepository userVoteOptionRepository;

    @Test
    void 유저가_해당_투표글에_투표한적이_있는지_확인한다() {
        // given
        userVoteOptionRepository.save(new UserVoteOption(1L, 1L, 1L));

        // when
        final boolean exists = userVoteOptionRepository.existsByUserIdAndVoteId(1L, 1L);

        // then
        assertThat(exists).isTrue();
    }
}
