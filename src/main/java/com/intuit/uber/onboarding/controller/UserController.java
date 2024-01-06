/*-Author------------------------------------
*- bibhas.abhishek@gmail.com
*- uber-onboarding: SignupController
*- 26 Nov 2021 12:01 AM
---Made with <3 in Delhi,India---------------
---Details-----------------------------------
*- Links:
-------------------------------------------*/

package com.intuit.uber.onboarding.controller;

import java.util.Optional;

import com.google.gson.Gson;
import com.intuit.uber.onboarding.exception.UserException;
import com.intuit.uber.onboarding.factory.CustomResponseEntityFactory;
import com.intuit.uber.onboarding.service.producer.UserSignupProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.uber.onboarding.model.entity.CustomResponseEntity;
import com.intuit.uber.onboarding.model.entity.User;
import com.intuit.uber.onboarding.service.AccountDetailsService;
import com.intuit.uber.onboarding.service.DriverOnboardingService;
import com.intuit.uber.onboarding.service.UserService;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DriverOnboardingService driverOnboardingService;

    @Autowired
    private AccountDetailsService accountDetailsService;

    @Autowired
    private UserSignupProducer userSignupProducer;

    @Autowired
    private CustomResponseEntityFactory customResponseEntityFactory;

    Gson gson = new Gson();

    @PostMapping("/user")
    public CustomResponseEntity signupUser(@RequestBody User user) {
        try {
            User dbUser = userService.userSignupService(user);
            driverOnboardingService.initOnboarding(dbUser);
            userSignupProducer.sendMessageToTopic(gson.toJson(dbUser));
            accountDetailsService.initAccountDetails(dbUser);
            return customResponseEntityFactory.getSuccessResponse(dbUser);
        } catch (UserException customException) {
            return customResponseEntityFactory.getBadRequestResponse(customException.getMessage());
        } catch (Exception exception) {
            return customResponseEntityFactory.getISEResponse();
        }
    }

    @GetMapping("/user/{id}")
    @Cacheable(value = "user", key = "#id")
    public CustomResponseEntity getCustomer(@PathVariable Long id) {
        Optional<User> user = userService.findUser(id);
        if (user.isPresent()) {
            return customResponseEntityFactory.getSuccessResponse(user);
        }
        return customResponseEntityFactory.getNotFoundResponse();
    }
}
