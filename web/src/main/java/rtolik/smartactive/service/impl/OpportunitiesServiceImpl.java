package rtolik.smartactive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rtolik.smartactive.models.Opportunities;
import rtolik.smartactive.models.Rate;
import rtolik.smartactive.models.enums.Status;
import rtolik.smartactive.repository.OpportunitiesRepository;
import rtolik.smartactive.service.CategoryService;
import rtolik.smartactive.service.OpportunitiesService;
import rtolik.smartactive.service.RateService;
import rtolik.smartactive.service.UserService;
import rtolik.smartactive.utils.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class OpportunitiesServiceImpl implements OpportunitiesService {

    @Autowired
    private OpportunitiesRepository opportunitiesRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RateService rateService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public Opportunities save(Opportunities service) {
        return opportunitiesRepository.save(service);
    }

    @Override
    public Opportunities findOne(Integer id) {
        return opportunitiesRepository.findOne(id);
    }

    @Override
    public List<Opportunities> findAll() {
        return opportunitiesRepository.findAll();
    }

    @Override
    public List<Opportunities> findAllActive() {
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
        Opportunities opportunities = opportunitiesRepository.findOne(opportunityId);
        if (opportunities.getUser().getName().equals(principal.getName())) {
            opportunitiesRepository.save(opportunities.setActive(active));
            return true;
        }
        return false;
    }

    @Override
    public List<Opportunities> findAllByCategory(Integer idOfCategory) {
        return opportunitiesRepository.findAllByCategory_Id(idOfCategory);
    }

    @Override
    public List<Opportunities> searchByWord(String word) {
        List<Opportunities> opportunitiesList = new ArrayList<>();
        for (Opportunities opportunities : findAll()) {
            for (String splitedWorld : word.split(" ")) {
                if (!splitedWorld.equals("and") && !splitedWorld.equals("и") &&
                        !splitedWorld.equals("і") && !splitedWorld.equals("ще") &&
                        !splitedWorld.equals("ещё") && !splitedWorld.equals("або") &&
                        !splitedWorld.equals("или") && !splitedWorld.equals(",") &&
                        !splitedWorld.equals("."))
                    if (opportunities.getOfferDescription().contains(word) && opportunities.getActive().equals(true))
                        opportunitiesList.add(opportunities);
            }
        }
        return opportunitiesList;
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
    public Opportunities createOpportunities(String opportunity, MultipartFile multipartFile, Principal principal) {
        String uuid = UUID.randomUUID().toString();
        Opportunities opportunities = JsonMapper.json(opportunity, Opportunities.class);
        opportunities.setStatus(Status.PUBLISHED);
        opportunities.setActive(true);
        opportunities.setUser(userService.findByName(principal.getName()));
        opportunities.setPhotoPath("/res/file/" + uuid + "/"
                + multipartFile.getOriginalFilename());
        opportunities.setCategory(categoryService.findOrCreate(opportunities.getCategory().getName()));
        String path = System.getProperty("catalina.home") + "/resources/hakathon/file/" + uuid + "/"
                + multipartFile.getOriginalFilename();
        System.out.println(path);
        File file = new File(path);

        try {
            file.getParentFile().mkdirs();
            multipartFile.transferTo(file);

            save(opportunities);


            for (int i = 0; i < 6; i++) {
                rateRepository.save(new Rate(i).setOpportunity(opportunities));
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error with file");
        }
        return opportunities;
    }

    @Override
    public List<Opportunities> findByMaxPrice(Double maxPrice) {
        return findAll().stream()
                .filter(opportunities -> opportunities.getPrice() <= maxPrice)
                .collect(toList());
    }

    @Override
    public List<Opportunities> filterListByMaxPrice(List<Opportunities> filterList, Double maxPrice) {
        return filterList.stream()
                .filter(opportunities -> opportunities.getPrice() <= maxPrice)
                .collect(toList());
    }

    @Override
    public List<Opportunities> filterListByCategory(List<Opportunities> filterList, Integer categoryId) {
        return filterList.stream()
                .filter(opportunities -> opportunities.getCategory().getId() == categoryId)
                .collect(toList());
    }

    @Override
    public List<Opportunities> filterListByKeywords(List<Opportunities> filterlist, String keywords) {
        return filterlist.stream()
                .filter(opportunities -> filterOpportunityBySplitedKeywords(opportunities.getId(), keywords)
                        &&
                        opportunities.getActive().equals(true))
                .collect(toList());
    }

    @Override
    public List<Opportunities> findByUser(Principal principal) {

        return userRepository.findByName(principal.getName()).getServices();
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
