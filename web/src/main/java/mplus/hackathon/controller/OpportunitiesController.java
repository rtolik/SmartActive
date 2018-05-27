package mplus.hackathon.controller;


import com.sun.org.apache.regexp.internal.RE;
import mplus.hackathon.config.websocket.ChatHandler;
import mplus.hackathon.config.websocket.utils.model.CategoryMessage;
import mplus.hackathon.models.Opportunities;
import mplus.hackathon.models.enums.Status;
import mplus.hackathon.service.OpportunitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by Anatoliy on 07.10.2017.
 */
@RequestMapping("/services")
@RestController
public class OpportunitiesController {

    @Autowired
    private OpportunitiesService opportunitiesService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    private ResponseEntity<Opportunities> add(@RequestParam(required = false) String name,
                                              @RequestParam(required = false) String offerDescription,
                                              @RequestParam(required = false) String category,
                                              @RequestParam(required = false) MultipartFile multipartFile,
                                              @RequestParam(required = false) Double price,
                                              Principal principal){
        if(name == null || category == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Opportunities opportunities = opportunitiesService.createOpportunities(name,offerDescription,
                multipartFile, price, category,principal);
        if(!ChatHandler.categoryMessages.stream().anyMatch(categoryMessage ->
                categoryMessage.getId().equals(opportunities.getCategory().getId()+"")))
            ChatHandler.categoryMessages.add(new CategoryMessage(opportunities.getCategory()));
        return  new ResponseEntity<>(opportunities,HttpStatus.OK);
    }

    @RequestMapping(value = "/findOne",method = RequestMethod.GET)
    private ResponseEntity<Opportunities> findOne(@RequestParam(required = false) Integer id){
        if(id == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(opportunitiesService.findOne(id),HttpStatus.OK);
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    private ResponseEntity<List<Opportunities>> findAll(){

        if(opportunitiesService.findAll()==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(opportunitiesService.findAll(),HttpStatus.OK);
    }

    @RequestMapping(value = "/findAllActive",method = RequestMethod.GET)
    private ResponseEntity<List<Opportunities>> findAllActive(){

        List<Opportunities> opportunities =opportunitiesService.findAllActive();
        if(opportunities ==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(opportunities,HttpStatus.OK);
    }

    @RequestMapping(value = "/findByUser",method = RequestMethod.GET)
    private ResponseEntity<List<Opportunities>> findByUser(Principal principal){

        if(opportunitiesService.findAll()==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(opportunitiesService.findByUser(principal),HttpStatus.OK);
    }

    @RequestMapping(value = "/searchByKeywords",method = RequestMethod.POST)
    private ResponseEntity<List<Opportunities>> findByKeywords(@RequestParam(required = false) String keywords){
        if(keywords == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(opportunitiesService.searchByWord(keywords),HttpStatus.OK);
    }

    @RequestMapping(value = "/saveToUser",method = RequestMethod.POST)
    private ResponseEntity saveToUser(@RequestParam(required = false)Principal principal,
                                      @RequestParam(required = false) Integer id){
        if(id==null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        opportunitiesService.saveOpportunitiesToUser(principal,id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/findAllInCategory",method = RequestMethod.GET)
    private ResponseEntity<List<Opportunities>> findAllInCategory(@RequestParam(required = false) Integer id){
        if(id == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(opportunitiesService.findAllByCategory(id),HttpStatus.OK);
    }

    @RequestMapping(value = "/setActive",method = RequestMethod.GET)
    private ResponseEntity setActive(@RequestParam(required = false) Boolean activity,
                                     @RequestParam(required = false) Principal principal,
                                     @RequestParam(required = false) Integer id)
    {
        if(activity==null || principal==null || id ==null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        opportunitiesService.setActive(activity,principal,id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/findByPrice")
    private ResponseEntity<List<Opportunities>> findByPrice(@RequestParam(required = false) Double price){
        if(price==null)
        {
            return new ResponseEntity<List<Opportunities>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Opportunities>>(opportunitiesService.findByMaxPrice(price),HttpStatus.OK);
    }

    @RequestMapping("/multipleFilter")
    private ResponseEntity<List<Opportunities>> multipleFilter(@RequestParam(required = false) Double maxPrice,
                                                               @RequestParam(required = false) Integer categoryId,
                                                               @RequestParam(required = false) String keywords){
        if(maxPrice==null&& categoryId==null&&keywords==null)
        {
            return  new ResponseEntity<List<Opportunities>>(HttpStatus.NO_CONTENT);
        }
        List<Opportunities> filterlist=opportunitiesService.findAll();
        if(maxPrice!=null)
        {
            filterlist=opportunitiesService.filterListByMaxPrice(filterlist,maxPrice);
        }
        if (categoryId!=null)
        {
            filterlist=opportunitiesService.filterListByCategory(filterlist,categoryId);
        }
        if(keywords!=null)
        {
            filterlist=opportunitiesService.filterListByKeywords(filterlist,keywords);
        }
        return new ResponseEntity<List<Opportunities>>(filterlist,HttpStatus.OK);
    }

    @RequestMapping("/loadStatuses")
    public ResponseEntity<List<Status>>loadStatuses(){
        return new ResponseEntity<>(Arrays.stream(Status.values())
                .collect(toList()), HttpStatus.OK);
    }

//    @RequestMapping("/delete")
//    private ResponseEntity delete(@RequestParam Integer id){
//        opportunitiesService.delete(id);
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
