package mplus.hackathon.service.impl;

import mplus.hackathon.models.Rate;
import mplus.hackathon.repository.RateRepository;
import mplus.hackathon.service.OpportunitiesService;
import mplus.hackathon.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import static java.util.stream.Collectors.toList;

/**
 * Created by Anatoliy on 08.10.2017.
 */
@Service
public class RateServiceImpl implements RateService{

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private OpportunitiesService opportunitiesService;

    @Override
    public Rate save(Rate rate) {
        return rateRepository.save(rate);
    }

    @Override
    public List<Rate> findAllInOpporunity(Integer opporunityId) {
        return opportunitiesService.findOne(opporunityId).getRates();
    }

    @Override
    public Rate findOne(Integer id) {
        return rateRepository.findOne(id);
    }

    @Override
    public void incrementVoices(Integer opportunityId, Integer val) {
        Integer tmp = findAllInOpporunity(opportunityId).stream()
                .filter(rate -> rate.getGrade().equals(val))
                .findFirst()
                .get()
                .getVoices();
        findAllInOpporunity(opportunityId).stream()
                .filter(rate -> rate.getGrade().equals(val))
                .findFirst().get().setVoices(tmp+1);
    }

    @Override
    public void countAvgInOpportunity(Integer opportnityId) {
        Double avg;
        Double coutGrades=0.0;
        Double countVoices=0.0;
        List<Rate> rates = findAllInOpporunity(opportnityId).stream()
                .filter(rate ->rate.getVoices()>0 )
                .collect(toList());
        for (Rate rate:rates) {
            coutGrades += rate.getGrade() * rate.getVoices() * 1.0;
            countVoices += rate.getVoices();
        }
        avg=coutGrades/countVoices;
        opportunitiesService.save(opportunitiesService.findOne(opportnityId).setRating(Math.round(avg * 100.0) / 100.0));
    }
}
