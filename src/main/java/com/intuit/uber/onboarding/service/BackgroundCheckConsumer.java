package com.intuit.uber.onboarding.service;

import com.google.gson.Gson;
import com.intuit.uber.onboarding.exception.CustomException;
import com.intuit.uber.onboarding.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BackgroundCheckConsumer {

    @Autowired
    private DriverOnboardingService driverOnboardingService;

    Gson gson = new Gson();
    @KafkaListener(topics = "NewUserTopic", groupId = "bgc-group")
    public void listenToNewUserKafkaTopic(String userString) {
        User user = gson.fromJson(userString, User.class);
        System.out.println("New User signup received by BGC consumer for userId: " + user.getId());
        try {
            driverOnboardingService.initBGC(user);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
