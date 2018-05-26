package mplus.hackathon.repository;

import mplus.hackathon.models.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Anatoliy on 08.10.2017.
 */
@Repository
public interface RateRepository extends JpaRepository<Rate,Integer>{
}
