package com.saas.multitenantspring.controller;

import com.saas.multitenantspring.config.SchemaResolver;
import com.saas.multitenantspring.model.User;
import com.saas.multitenantspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/")
public class GlobalController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "Running";
    }


    @RequestMapping(value = "/c", method = RequestMethod.GET)
    public String createUser(){

        SchemaResolver.setCurrentTenant("master");
        User user = new User();
        user.setFirstName("naveen");
        user.setFirstName("tulse");
        user.setUsername(LocalDate.now().toString());
        userRepository.save(user);
        return "schema one user created";
    }

    @RequestMapping(value = "/b", method = RequestMethod.GET)
    public String createUserOtherSchema(){

        SchemaResolver.setCurrentTenant("public");
        User user = new User();
        user.setFirstName("naveen");
        user.setFirstName("tulsi");
        user.setUsername(LocalDate.now().toString());
        userRepository.save(user);
        return "schema one user created";
    }
}
