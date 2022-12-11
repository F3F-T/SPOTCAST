package f3f.domain.category.dao;

import f3f.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsByCategoryName(String categoryName);
}
