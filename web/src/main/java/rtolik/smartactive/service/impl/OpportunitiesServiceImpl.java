package rtolik.smartactive.service.impl;

import rtolik.smartactive.models.Opportunities;
import rtolik.smartactive.models.Rate;
import rtolik.smartactive.models.enums.Status;
import rtolik.smartactive.repository.OpportunitiesRepository;
import rtolik.smartactive.service.CategoryService;
import rtolik.smartactive.service.OpportunitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    public void delete(Integer id) {
        opportunitiesRepository.delete(id);
    }

    @Override
    public void setActive(Boolean active, Principal principal, Integer opportunityId) {
        userService.findByName(principal.getName()).getServices()
                .stream()
                .filter(opportunities -> opportunities.getId().equals(opportunityId))
                .forEach(opportunities -> {
                    updateOppor(opportunities.setActive(active));
                    System.out.println("I`m here");
                });

    }

    private void updateOppor(Opportunities opportunities){
        opportunitiesRepository.save(opportunities);
    }

    @Override
    public List<Opportunities> findAllByCategory(Integer idOfCategory) {
        return findAll()
                .stream()
                .filter(opportunities -> opportunities.getCategory().getId().equals(idOfCategory)
                        &&
                        opportunities.getActive())
                .collect(toList());
    }

    @Override
    public List<Opportunities> searchByWord(String word) {
        List<Opportunities> opportunitiesList = new ArrayList<>();
        for (Opportunities opportunities : findAll()) {
            for(String splitedWorld:word.split(" ")) {
                if(!splitedWorld.equals("and")
                        &&!splitedWorld.equals("и")
                        &&!splitedWorld.equals("і")
                        &&!splitedWorld.equals("ще")
                        &&!splitedWorld.equals("ещё")
                        &&!splitedWorld.equals("або")
                        &&!splitedWorld.equals("или")
                        &&!splitedWorld.equals(",")
                        &&!splitedWorld.equals("."))
                    if (opportunities.getOfferDescription().contains(word) && opportunities.getActive().equals(true))
                        opportunitiesList.add(opportunities);
            }
        }

        return opportunitiesList;
    }

    @Override
    public void saveOpportunitiesToUser(Principal principal, Integer id) {
        findOne(id).setUser(userService.findByName(principal.getName()));
    }

    @Override
    public Opportunities createOpportunities(String opportunity, MultipartFile multipartFile, Principal principal) {
        String uuid = UUID.randomUUID().toString();
        Opportunities opportunities = JsonMapper.json(opportunity,Opportunities.class);
        opportunities.setStatus(Status.PUBLISHED).setActive(true).setUser(userService.findByName(principal.getName()))
                     .setPhotoPath("/res/file/" + uuid + "/" + multipartFile.getOriginalFilename())
                     .setCategory(categoryService.findOrCreate(opportunities.getCategory().getName()));
        String path = System.getProperty("catalina.home") + "/resources/hakathon/file/" + uuid + "/"
                + multipartFile.getOriginalFilename();
        System.out.println(path);
        File file = new File(path);

        try {
            file.getParentFile().mkdirs();
            multipartFile.transferTo(file);

            save(opportunities);
            for (int i=0;i<6;i++) {
                rateService.save(new Rate(i).setOpportunity(opportunities));
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

        return userService.findByName(principal.getName()).getServices();
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
