package rtolik.smartactive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import rtolik.smartactive.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Anatoliy on 08.10.2017.
 */
@RequestMapping("/rate")
@RestController
public class RateController {

    @Autowired
    private RateService rateService;


    @GetMapping("/incrementVoices")
    private ResponseEntity incrementRate(@RequestParam(required = false) Integer opportunityId,
                                         @RequestParam(required = false) Integer val){
        if(opportunityId==null||val==null)
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        rateService.incrementVoices(opportunityId,val);
        rateService.countAvgInOpportunity(opportunityId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/countAvg")
    private  ResponseEntity<Integer> countAvg(@RequestParam Integer opportId){
        if (opportId==null)
            return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<Integer>(rateService.countAvgInOpportunity(opportId),HttpStatus.OK);
    }
}
