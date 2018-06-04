package rtolik.smartactive.repository;

import rtolik.smartactive.models.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Anatoliy on 07.10.2017.
 */
@Repository
public interface OpportunitiesRepository extends JpaRepository<Opportunity,Integer>{
    List<Opportunity> findAllByCategory_Name(String category_name);

    List<Opportunity> findAllByCategory_Id(Integer integer);

    List<Opportunity> findAllByUser_Id(Integer user_id);

    List<Opportunity> findAllByUser_Name(String name);
}
