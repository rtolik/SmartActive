package rtolik.smartactive.controller;

import rtolik.smartactive.config.Constants;
import rtolik.smartactive.models.User;
import rtolik.smartactive.repository.UserRepository;
import rtolik.smartactive.service.MailSenderService;
import rtolik.smartactive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.password.PasswordEncoder;


import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Anatoliy on 07.10.2017.
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @GetMapping("/g")
    private ResponseEntity<String> some(Principal principal) {
        return new ResponseEntity<>(principal.toString(), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    private ResponseEntity<User> save(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String password,
                                      @RequestParam(required = false) String email,
                                      @RequestParam(required = false) String color,
                                      @RequestParam String phone){
        String uuid = UUID.randomUUID().toString();
        if(name==null || password==null || email==null||color==null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userService.save(new User(name,password,email,color,true,phone,uuid)),
                                    HttpStatus.OK);

    }

    @GetMapping("/findOne/{id}")
    private ResponseEntity<User> findOne(@PathVariable Integer id) {

        if (userService.findOne(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userService.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/getUserByPrincipal")
    private ResponseEntity<User> getUserByPrincipal(Principal principal) {
        if(principal == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        return new ResponseEntity<>(userRepository.findByName(principal.getName()), HttpStatus.OK);
    }


    @GetMapping("/findAll")
    private ResponseEntity<List<User>> findOne() {

        if (userService.findAll() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/sendEmail")
    public ResponseEntity sendEmail(@RequestParam String email){

        mailSenderService.sendMail("Forgot password",
                Constants.LINK+"confirm/"+userService.findByEmail(email).getUuid(),email);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/confirm/{uuid}")
    public ResponseEntity sendEmail(@RequestParam String password, @PathVariable String uuid){
        userService.updatePassword(uuid,password);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/validateName")
    private ResponseEntity<Boolean> validateName(@RequestParam(required = false) String name) {
        if (name == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userService.validateName(name), HttpStatus.OK);
    }

    @GetMapping("/validateEmail")
    private ResponseEntity<Boolean> validateEmail(@RequestParam(required = false) String email) {
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userService.validateEmail(email), HttpStatus.OK);
    }

    @GetMapping("/deleteUser")
    private ResponseEntity selfdelete(@RequestParam(required = false) Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        userService.selfDelete(principal);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/login")
    private ResponseEntity<Boolean> login(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) String password) {
        if (name == null || password == null)
            return new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
        if (userService.login(name, password) == null)
            return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Boolean>(userService.login(name, password), HttpStatus.OK);
    }

    @PostMapping(value = "/ban")
    private ResponseEntity ban(@RequestParam String name) {
        userService.appeal(name);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Scheduled(fixedDelay = 86400)
    private void unlockUser() {
        userService.scheduledUnban();
    }

    @GetMapping("/getPrincipal")
    private ResponseEntity<Boolean> getPrincipal(Principal principal){
        return new ResponseEntity<>(Optional.ofNullable(principal).isPresent(),HttpStatus.OK);
    }

    @PostMapping(value = "/findByName")
    private ResponseEntity<User> findByName(@RequestParam String name) {

        return new ResponseEntity<>(userService.findByName(name),HttpStatus.OK);
    }

    @GetMapping()
    private ResponseEntity<User> getUser(Principal principal){
        return ResponseEntity.ok(userService.findByName(principal.getName()));
    }
}
