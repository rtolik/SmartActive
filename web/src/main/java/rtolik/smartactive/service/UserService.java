package rtolik.smartactive.service;


import rtolik.smartactive.models.Opportunity;
import rtolik.smartactive.models.User;

import java.security.Principal;
import java.util.List;

public interface UserService {

    User save(User user);

    User findOne(Integer id);

    List<User> findAll();

    void delete(Integer id);

    Boolean validateName(String name);

    Boolean validateEmail(String email);

    void selfDelete(Principal principal);

    void ban(Integer id);

    void appeal(String name);

    void scheduledUnban();

    Boolean login(String name, String password);

    User findByName(String name);

    User findByEmail(String email);

    void updatePassword(String userUuid,String password);

    List<Opportunity> findLiked(Integer userId);

    Boolean like(Integer userId, Integer opportunityId);

}
