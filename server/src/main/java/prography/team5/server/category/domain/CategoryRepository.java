package prography.team5.server.category.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prography.team5.server.user.domain.UserType;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(final String name);

    List<Category> findAllByIdIn(final List<Long> ids);

    @Query("SELECT c FROM Category c JOIN c.userTypes ut WHERE ut = :userType")
    List<Category> findAllByUserType(@Param("userType") UserType userType);
}
