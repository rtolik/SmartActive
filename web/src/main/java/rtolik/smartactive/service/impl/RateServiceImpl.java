package rtolik.smartactive.service.impl;

import rtolik.smartactive.models.Rate;
import rtolik.smartactive.repository.RateRepository;
import rtolik.smartactive.service.OpportunitiesService;
import rtolik.smartactive.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import static java.util.stream.Collectors.toList;

/**
 * Created by Anatoliy on 08.10.2017.
 */
@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private OpportunitiesService opportunitiesService;

    @Override
    public Rate save(Rate rate) {
        return rateRepository.save(rate);
    }

    @Override
    public List<Rate> findAllRatesInOpporunity(Integer opporunityId) {
        return opportunitiesService.findOne(opporunityId).getRates();
    }

    @Override
    public Rate findOne(Integer id) {
        return rateRepository.findOne(id);
    }

    @Override
    public void incrementVoices(Integer opportunityId, Integer val) {
        List<Rate> rates=findAllRatesInOpporunity(opportunityId);
        Integer tmp = rates.stream()
                .filter(rate -> rate.getGrade().equals(val))
                .findFirst()
                .get()
                .getVoices();
        rates.forEach(rate -> {
            if (rate.equals(val))
                save(rate.setVoices(tmp+1));
        });
    }

    @Override
    public Integer countAvgInOpportunity(Integer opportnityId) {
        Double avg;
        Double coutGrades=0.0;
        Double countVoices=0.0;
        List<Rate> rates = findAllRatesInOpporunity(opportnityId).stream()
                .filter(rate ->rate.getVoices()>0 )
                .collect(toList());
        for (Rate rate:rates) {
            coutGrades += rate.getGrade() * rate.getVoices() * 1.0;
            countVoices += rate.getVoices();
        }
        avg=coutGrades/countVoices;
        opportunitiesService.save(opportunitiesService.findOne(opportnityId).setRating(Math.round(avg * 100.0) / 100.0));
        Long lon = Math.round(avg);
        return lon.intValue();
    }
}
