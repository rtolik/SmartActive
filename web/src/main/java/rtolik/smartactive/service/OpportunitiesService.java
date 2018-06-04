package rtolik.smartactive.service;

import rtolik.smartactive.models.Opportunity;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface OpportunitiesService {

    Opportunity save(Opportunity service);

    Opportunity findOne(Integer id);

    List<Opportunity> findAll();

    List<Opportunity> findAllActive();

    Boolean delete(Integer id);

    Boolean setActive(Boolean active, Principal principal,Integer opportunityId);

    List<Opportunity> findAllByCategory(Integer idOfCategory);

    List<Opportunity> searchByWord(String word);

    Boolean saveOpportunitiesToUser(Principal principal,Integer id);

    void saveOpportunitiesToUserLiked(Principal principal,Integer id);

    Opportunity createOpportunities(String opportunity,
                                    MultipartFile multipartFile, Integer id);

    List<Opportunity> findByMaxPrice(Double maxPrice);

    List<Opportunity> filterListByMaxPrice(List<Opportunity> filterList, Double maxPrice);

    List<Opportunity> filterListByCategory(List<Opportunity> filterList, Integer categoryId);

    List<Opportunity> filterListByKeywords(List<Opportunity> filterlist, String keywords);

    List<Opportunity> findByUser(Principal principal);

    //List<Opportunity> filterListBySplitedKeywords(List<Opportunity> filterlist, String keywords);
}
