package prography.team5.server.category.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import prography.team5.server.user.domain.UserType;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void 특정_유저_타입에_해당하는_카테고리를_조회한다() {
        // given
        final Category category1 = new Category("카테고리 1");
        category1.addUserType(UserType.STUDENT);
        category1.addUserType(UserType.JOB_SEEKER);
        categoryRepository.save(category1);

        final Category category2 = new Category("카테고리 2");
        category2.addUserType(UserType.JOB_SEEKER);
        categoryRepository.save(category2);

        // when
        final List<Category> studentCategories = categoryRepository.findAllByUserType(UserType.STUDENT);
        final List<Category> jobSeekerCategories = categoryRepository.findAllByUserType(UserType.JOB_SEEKER);
        final List<Category> newEmployeeCategories = categoryRepository.findAllByUserType(UserType.NEW_EMPLOYEE);

        // then
        assertThat(studentCategories).hasSize(1);
        assertThat(jobSeekerCategories).hasSize(2);
        assertThat(newEmployeeCategories).hasSize(0);
    }
}
