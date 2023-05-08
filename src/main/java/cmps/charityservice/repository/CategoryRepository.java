package cmps.charityservice.repository;

import cmps.charityservice.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    List<Category> findAllByCategoryName(Long categoryName, Pageable pageable);
}
