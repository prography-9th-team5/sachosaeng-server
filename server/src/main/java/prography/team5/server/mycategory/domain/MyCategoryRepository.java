package prography.team5.server.mycategory.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyCategoryRepository extends JpaRepository<MyCategory, Long> {

    List<MyCategory> findAllByUserId(long userId);

    @Query("SELECT mc FROM MyCategory mc JOIN FETCH mc.category c WHERE mc.userId = :userId ORDER BY c.priority ASC")
    List<MyCategory> findAllByUserIdOrderByCategoryPriorityAsc(@Param("userId") long userId);

    void deleteAllByUserId(long userId);
}
