package rtolik.smartactive.repository;

import rtolik.smartactive.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Anatoliy on 07.10.2017.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
