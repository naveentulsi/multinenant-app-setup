package com.saas.multitenantspring.controller;

import com.saas.multitenantspring.config.SchemaResolver;
import com.saas.multitenantspring.model.User;
import com.saas.multitenantspring.repository.UserRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class GlobalController {

    private static Log logger = LogFactory.getLog(GlobalController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "Running";
    }


    @RequestMapping(value = "/c", method = RequestMethod.GET)
    public String createUser(HttpServletRequest request){

        logger.info("Start createUser");
        logger.info("Tenant Value :: " + request.getHeader("tenant"));
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

        logger.info("Start createUserOtherSchema");

        SchemaResolver.setCurrentTenant("public");
        User user = new User();
        user.setFirstName("naveen");
        user.setFirstName("tulsi");
        user.setUsername(LocalDate.now().toString());
        userRepository.save(user);
        return "schema one user created";
    }
}
