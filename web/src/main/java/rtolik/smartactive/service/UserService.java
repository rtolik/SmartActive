package rtolik.smartactive.service;


import rtolik.smartactive.models.Opportunities;
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

    void ban(String name,Boolean isaActive);

    void appeal(Integer id);


    void appealByName(String name);

    Boolean login(String name, String password);

    User findByName(String name);

    User findByEmail(String email);

    void updatePassword(String userUuid,String password);

    List<Opportunities> findLiked(Integer userId);

    Boolean like(Integer userId, Integer opportunityId);

}
