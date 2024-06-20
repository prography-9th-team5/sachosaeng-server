package prography.team5.server.mycategory.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyCategoryRepository extends JpaRepository<MyCategory, Long> {

    List<MyCategory> findAllByUserId(long userId);

    void deleteAllByUserId(long userId);
}
