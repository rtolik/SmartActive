package rtolik.smartactive.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rtolik.smartactive.models.Opportunity;
import rtolik.smartactive.models.Rate;
import rtolik.smartactive.models.User;
import rtolik.smartactive.models.enums.Status;
import rtolik.smartactive.repository.OpportunitiesRepository;
import rtolik.smartactive.service.CategoryService;
import rtolik.smartactive.service.OpportunitiesService;
import rtolik.smartactive.service.RateService;
import rtolik.smartactive.service.UserService;
import rtolik.smartactive.service.utils.FileBuilder;
import rtolik.smartactive.utils.JsonMapper;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OpportunitiesServiceImpl implements OpportunitiesService {
    private static final Logger LOGGER = Logger.getLogger(OpportunitiesServiceImpl.class);

    @Autowired
    private OpportunitiesRepository opportunitiesRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RateService rateService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileBuilder fileBuilder;

    @Override
    public Opportunity save(Opportunity service) {
        return opportunitiesRepository.save(service);
    }

    @Override
    public Opportunity findOne(Integer id) {
        return opportunitiesRepository.findOne(id);
    }

    @Override
    public List<Opportunity> findAll() {
        return opportunitiesRepository.findAll();
    }

    @Override
    public List<Opportunity> findAllActive() {
        return findAll().stream().filter(opportunities -> opportunities.getActive().equals(true)).collect(toList());
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            opportunitiesRepository.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean setActive(Boolean active, Principal principal, Integer opportunityId) {
        Opportunity opportunity = opportunitiesRepository.findOne(opportunityId);
        if (opportunity.getUser().getName().equals(principal.getName())) {
            opportunitiesRepository.save(opportunity.setActive(active));
            return true;
        }
        return false;
    }

    @Override
    public List<Opportunity> findAllByCategory(Integer idOfCategory) {
        return opportunitiesRepository.findAllByCategory_Id(idOfCategory);
    }

    @Override
    public List<Opportunity> searchByWord(String word) {
        List<Opportunity> opportunityList = new ArrayList<>();
        for (Opportunity opportunity : findAll()) {
            for (String splitedWorld : word.split(" ")) {
                if (!splitedWorld.equals("and") && !splitedWorld.equals("и") &&
                        !splitedWorld.equals("і") && !splitedWorld.equals("ще") &&
                        !splitedWorld.equals("ещё") && !splitedWorld.equals("або") &&
                        !splitedWorld.equals("или") && !splitedWorld.equals(",") &&
                        !splitedWorld.equals("."))
                    if (opportunity.getOfferDescription().contains(word) && opportunity.getActive().equals(true))
                        opportunityList.add(opportunity);
            }
        }
        return opportunityList;
    }

    @Override
    public Boolean saveOpportunitiesToUser(Principal principal, Integer id) {
        try {
            opportunitiesRepository.save(findOne(id).setUser(userService.findByName(principal.getName())));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void saveOpportunitiesToUserLiked(Principal principal, Integer id) {
        User user= userService.findByName(principal.getName());
        List<Opportunity> liked = user.getLiked();
        liked.add(findOne(id));
        user.setLiked(liked);
    }

    @Override
    public Opportunity createOpportunities(String opportunity, MultipartFile multipartFile, Integer id) {
        LOGGER.info(id ==null);
        Opportunity opportunities = JsonMapper.json(opportunity, Opportunity.class);
        opportunities
                .setStatus(Status.PUBLISHED)
                .setActive(true)
                .setUser(userService.findOne(id))
                .setPhotoPath(fileBuilder.saveFile(multipartFile))
                .setCategory(categoryService.findOrCreate(opportunities.getCategory().getName()));
        save(opportunities);
        for (int i = 0; i < 6; i++) {
            rateService.save(new Rate(i).setOpportunity(opportunities));
        }
        return opportunities;
    }

    @Override
    public List<Opportunity> findByMaxPrice(Double maxPrice) {
        return findAll().stream()
                .filter(opportunities -> opportunities.getPrice() <= maxPrice)
                .collect(toList());
    }

    @Override
    public List<Opportunity> filterListByMaxPrice(List<Opportunity> filterList, Double maxPrice) {
        return filterList.stream()
                .filter(opportunities -> opportunities.getPrice() <= maxPrice)
                .collect(toList());
    }

    @Override
    public List<Opportunity> filterListByCategory(List<Opportunity> filterList, Integer categoryId) {
        return filterList.stream()
                .filter(opportunities -> opportunities.getCategory().getId().equals(categoryId))
                .collect(toList());
    }

    @Override
    public List<Opportunity> filterListByKeywords(List<Opportunity> filterlist, String keywords) {
        return filterlist.stream()
                .filter(opportunities ->
                        filterOpportunityBySplitedKeywords(opportunities.getId(), keywords) &&
                                opportunities.getActive())
                .collect(toList());
    }

    @Override
    public List<Opportunity> findByUser(Principal principal) {
        return opportunitiesRepository.findAllByUser_Name(principal.getName());
    }


    private Boolean filterOpportunityBySplitedKeywords(Integer opportunitiesId, String keywords) {
        for (String splitedWorld : keywords.split(" ")) {
            if (!splitedWorld.equals("and")
                    && !splitedWorld.equals(",")
                    && !splitedWorld.equals("."))
                if (findOne(opportunitiesId).getOfferDescription().toLowerCase().contains(splitedWorld.toLowerCase())) {
                    return true;
                }
        }
        return false;
    }
}
