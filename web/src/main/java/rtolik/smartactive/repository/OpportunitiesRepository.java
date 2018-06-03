package rtolik.smartactive.repository;

import rtolik.smartactive.models.Opportunities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Anatoliy on 07.10.2017.
 */
@Repository
public interface OpportunitiesRepository extends JpaRepository<Opportunities,Integer>{
    List<Opportunities> findAllByCategory_Name(String category_name);

    List<Opportunities> findAllByCategory_Id(Integer integer);
}
