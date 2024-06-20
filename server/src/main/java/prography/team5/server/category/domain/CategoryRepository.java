package prography.team5.server.category.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(final String name);

    List<Category> findAllByIdIn(final List<Long> ids);
}
