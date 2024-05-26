package prography.team5.server.domain.category;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkCategoryRepository extends JpaRepository<BookmarkCategory, Long> {

    List<BookmarkCategory> findAllByUserId(long userId);

    void deleteAllByUserId(long userId);

}
