package mplus.hackathon.service.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import mplus.hackathon.models.User;
import mplus.hackathon.repository.UserRepository;

import mplus.hackathon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;


import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
    public void ban(String name, Boolean isActive) {
        if (findByName(name).getBansCount() - 1 > 0) {
            saveUser(findByName(name).setBansCount(findByName(name).getBansCount() - 1));
        } else {
            saveUser(findByName(name).setBansCount(5).setActive(isActive));
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
    public void appeal(Integer id) {//TODO write method to ban for time
        findOne(id).setNumOfAppeals(findOne(id).getNumOfAppeals() + 1);
    }


    @Override
    public void appealByName(String name) {
        appeal(findByName(name).getId());
    }
}
