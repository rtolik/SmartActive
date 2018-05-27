package mplus.hackathon.service.impl;

import mplus.hackathon.models.Category;
import mplus.hackathon.models.Opportunities;
import mplus.hackathon.models.Rate;
import mplus.hackathon.models.User;
import mplus.hackathon.models.enums.Status;
import mplus.hackathon.repository.CategoryRepository;
import mplus.hackathon.repository.OpportunitiesRepository;
import mplus.hackathon.repository.RateRepository;
import mplus.hackathon.repository.UserRepository;
import mplus.hackathon.service.OpportunitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private UserRepository userRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
        userRepository.findByName(principal.getName()).getServices()
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
        findOne(id).setUser(userRepository.findByName(principal.getName()));
    }

    @Override
    public Opportunities createOpportunities(String name, String offerDescription, MultipartFile multipartFile, Double price, String category,Principal principal) {

        String uuid = UUID.randomUUID().toString();
        Opportunities opportunities = new Opportunities();
        opportunities.setName(name);
        opportunities.setOfferDescription(offerDescription);
        opportunities.setPrice(price);
        opportunities.setStatus(Status.PUBLISHED);
        opportunities.setActive(true);
        opportunities.setUser(userRepository.findByName(principal.getName()));
        opportunities.setPhotoPath("/res/file/" + uuid + "/"
                + multipartFile.getOriginalFilename());
        opportunities.setCategory((categoryRepository.findAll().stream().anyMatch(category1 ->
                category1.getName().toLowerCase().equals(category.toLowerCase()))) ?
                categoryRepository.findAll().stream().filter(category1 -> category1.getName().toLowerCase()
                        .equals(category.toLowerCase())).findFirst().get() : categoryRepository.save(new Category()
                .setName(category)));

        String path = System.getProperty("catalina.home") + "/resources/hakathon/file/" + uuid + "/"
                + multipartFile.getOriginalFilename();
        System.out.println(path);
        File file = new File(path);

        try {
            file.getParentFile().mkdirs();
            multipartFile.transferTo(file);

            save(opportunities);



            for (int i=0;i<6;i++) {
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
