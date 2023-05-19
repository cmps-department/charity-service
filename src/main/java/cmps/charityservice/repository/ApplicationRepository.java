package cmps.charityservice.repository;

import cmps.charityservice.model.Application;
import cmps.charityservice.model.Category;
import cmps.charityservice.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, String> {

    @Query("SELECT c FROM Application c WHERE (:status is null or c.status = :status)"
            + " and (:category is null or c.category = :category)"
            + " and (:authorId is null or c.authorId = :authorId)")
    List<Application> findAllByFilters(@Param("status") Status status,
                                       @Param("category") Category category,
                                       @Param("authorId") String authorId);
}
