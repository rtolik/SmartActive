package rtolik.smartactive.service.impl;

import rtolik.smartactive.models.Opportunities;
import rtolik.smartactive.models.User;
import rtolik.smartactive.repository.UserRepository;

import rtolik.smartactive.service.OpportunitiesService;
import rtolik.smartactive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static rtolik.smartactive.service.utils.Utility.getUnbanDate;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OpportunitiesService opportunitiesService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setBansCount(5);
        return userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findOne(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public Boolean validateName(String name) {
        return findAll().stream()
                .filter(user -> user.getName().equals(name))
                .collect(toList())
                .isEmpty();
    }

    @Override
    public Boolean validateEmail(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .collect(toList())
                .isEmpty();
    }

    @Override
    public void selfDelete(Principal principal) {
        delete(userRepository.findByName(principal.getName()).getId());
    }

    @Override
    public void ban(Integer id) {
        User user= findOne(id);
        if (user.getBansCount() - 1 > 0) {
            saveUser(user.setBansCount(user.getBansCount() - 1).setActive(false)
                    .setDateOfban(LocalDate.now().toString()));
        } else {
            saveUser(user.setBansCount(0).setActive(false).setDateOfban(LocalDate.now().toString()));
        }

    }

    @Override
    public Boolean login(String name, String password) {
        User current = findByName(name);
        if (current == null)
            return null;
        if (current.getPassword().equals(encoder.encode(password)) && current.getActive())
            return true;
        return false;
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User findByEmail(String email) {
        return  findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().get();
    }

    public User findByUuid(String uuid){
        return findAll().stream()
                .filter(user -> user.getUuid().equalsIgnoreCase(uuid))
                .findFirst()
                .get();
    }

    @Override
    public void updatePassword(String userUuid,String password) {

        findByUuid(userUuid).setPassword(encoder.encode(password)).setUuid(UUID.randomUUID().toString());

    }

    @Override
    public void appeal(String name) {
        User user=findByName(name);
        user.setNumOfAppeals(user.getNumOfAppeals() + 1);
        if (user.getNumOfAppeals()>=5) {
            user.setNumOfAppeals(0);
            ban(user.getId());
        }
        save(user);
    }

    @Override
    public List<Opportunities> findLiked(Integer userId) {
        return findOne(userId).getLiked();
    }

    @Override
    public Boolean like(Integer userId ,Integer opportunityId) {
        try {

            User tmp= findOne(userId);
            List<Opportunities> liked= tmp.getLiked();
            liked.add(opportunitiesService.findOne(opportunityId));
            save(tmp.setLiked(liked));
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void scheduledUnban() {
        List<User> users = userRepository.findAll().stream()
                .filter(
                        user -> !user.getActive()&&user.getBansCount()>0
                ).collect(toList());
        users.forEach(
                user -> {
                    if (getUnbanDate(user.getDateOfban()).equals(LocalDate.now().toString()))
                        save(user.setActive(true).setNumOfAppeals(0));
                }
        );
    }
}
