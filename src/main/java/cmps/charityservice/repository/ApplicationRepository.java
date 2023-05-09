package cmps.charityservice.repository;

import cmps.charityservice.model.Application;
import cmps.charityservice.model.Category;
import cmps.charityservice.model.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ApplicationRepository extends PagingAndSortingRepository<Application, String> {

    List<Application> findAllByStatus(Status status, Pageable pageable);
    List<Application> findAllByStatusAndCategory(Status status, Category category, Pageable pageable);
}
