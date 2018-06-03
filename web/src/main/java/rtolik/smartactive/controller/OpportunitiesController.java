package rtolik.smartactive.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rtolik.smartactive.config.websocket.ChatHandler;
import rtolik.smartactive.config.websocket.utils.model.CategoryMessage;
import rtolik.smartactive.models.Opportunities;
import rtolik.smartactive.models.enums.Status;
import rtolik.smartactive.service.OpportunitiesService;

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

    @PostMapping("/add")
    private ResponseEntity<Opportunities> add(@RequestParam String opportunity, @RequestParam MultipartFile multipartFile, Principal principal) {
        Opportunities opportunities = opportunitiesService.createOpportunities(opportunity, multipartFile, principal);
        if (ChatHandler.categoryMessages.stream().noneMatch(categoryMessage ->
                categoryMessage.getId().equals(opportunities.getCategory().getId())))
            ChatHandler.categoryMessages.add(new CategoryMessage(opportunities.getCategory()));
        return new ResponseEntity<>(opportunities, HttpStatus.OK);
    }

    @GetMapping("/findOne/{id}")
    private ResponseEntity<Opportunities> findOne(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(opportunitiesService.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    private ResponseEntity<List<Opportunities>> findAll() {
        if (opportunitiesService.findAll() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(opportunitiesService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/findAllActive")
    private ResponseEntity<List<Opportunities>> findAllActive() {
        List<Opportunities> opportunities = opportunitiesService.findAllActive();
        if (opportunities == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(opportunities, HttpStatus.OK);
    }

    @GetMapping("/findByUser")
    private ResponseEntity<List<Opportunities>> findByUser(Principal principal) {
        if (opportunitiesService.findAll() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(opportunitiesService.findByUser(principal), HttpStatus.OK);
    }

    @GetMapping("/searchByKeywords")
    private ResponseEntity<List<Opportunities>> findByKeywords(@RequestParam String keywords) {
        if (keywords == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(opportunitiesService.searchByWord(keywords), HttpStatus.OK);
    }

    @PostMapping("/saveToUser/{id}")
    private ResponseEntity saveToUser(Principal principal,
                                      @PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        opportunitiesService.saveOpportunitiesToUser(principal, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/findAllInCategory/{id}")
    private ResponseEntity<List<Opportunities>> findAllInCategory(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(opportunitiesService.findAllByCategory(id), HttpStatus.OK);
    }

    @PostMapping("/setActive/{id}")
    private ResponseEntity setActive(@RequestParam Boolean activity, Principal principal,
                                     @PathVariable Integer id) {
        if (activity == null || principal == null || id == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        opportunitiesService.setActive(activity, principal, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/findByPrice")
    private ResponseEntity<List<Opportunities>> findByPrice(@RequestParam Double price) {
        if (price == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(opportunitiesService.findByMaxPrice(price), HttpStatus.OK);
    }

    @GetMapping("/multipleFilter")
    private ResponseEntity<List<Opportunities>> multipleFilter(@RequestParam(required = false) Double maxPrice,
                                                               @RequestParam(required = false) Integer categoryId,
                                                               @RequestParam(required = false) String keywords) {
        if (maxPrice == null && categoryId == null && keywords == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Opportunities> filterlist = opportunitiesService.findAll();
        if (maxPrice != null) {
            filterlist = opportunitiesService.filterListByMaxPrice(filterlist, maxPrice);
        }
        if (categoryId != null) {
            filterlist = opportunitiesService.filterListByCategory(filterlist, categoryId);
        }
        if (keywords != null) {
            filterlist = opportunitiesService.filterListByKeywords(filterlist, keywords);
        }
        return new ResponseEntity<>(filterlist, HttpStatus.OK);
    }

    @GetMapping("/loadStatuses")
    public ResponseEntity<List<Status>> loadStatuses() {
        return new ResponseEntity<>(Arrays.stream(Status.values())
                .collect(toList()), HttpStatus.OK);
    }

//    @RequestMapping("/delete")
//    private ResponseEntity delete(@RequestParam Integer id){
//        opportunitiesService.delete(id);
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
