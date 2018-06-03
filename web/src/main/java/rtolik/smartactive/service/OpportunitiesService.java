package rtolik.smartactive.service;

import rtolik.smartactive.models.Opportunities;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface OpportunitiesService {

    Opportunities save(Opportunities service);

    Opportunities findOne(Integer id);

    List<Opportunities> findAll();

    List<Opportunities> findAllActive();

    void delete(Integer id);

    void setActive(Boolean active, Principal principal,Integer opportunityId);

    List<Opportunities> findAllByCategory(Integer idOfCategory);

    List<Opportunities> searchByWord(String word);

    void saveOpportunitiesToUser(Principal principal,Integer id);

    void saveOpportunitiesToUserLiked(Principal principal,Integer id);

    Opportunities createOpportunities(String opportunity,
                                      MultipartFile multipartFile, Principal principal);

    List<Opportunities> findByMaxPrice(Double maxPrice);

    List<Opportunities> filterListByMaxPrice(List<Opportunities> filterList, Double maxPrice);

    List<Opportunities> filterListByCategory(List<Opportunities> filterList, Integer categoryId);

    List<Opportunities> filterListByKeywords(List<Opportunities> filterlist, String keywords);

    List<Opportunities> findByUser(Principal principal);

    //List<Opportunities> filterListBySplitedKeywords(List<Opportunities> filterlist, String keywords);
}
