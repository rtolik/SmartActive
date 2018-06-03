package rtolik.smartactive.service;

import rtolik.smartactive.models.Rate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Anatoliy on 08.10.2017.
 */
@Service
public interface RateService {
    Rate save(Rate rate);

    List<Rate> findAllRatesInOpporunity(Integer opporunityId);

    Rate findOne(Integer id);

    void incrementVoices(Integer opportunityId, Integer val);

    Integer countAvgInOpportunity(Integer opportnityId);

}
