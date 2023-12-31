package com.intuit.uber.onboarding.service;

import com.google.gson.Gson;
import com.intuit.uber.onboarding.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DocumentCollectionConsumer {
    @Autowired
    private DriverOnboardingService driverOnboardingService;

    Gson gson = new Gson();
    @KafkaListener(topics = "NewUserTopic", groupId = "docCollection-group")
    public void listenToCodeDecodeKafkaTopic(String userString) {
        User user = gson.fromJson(userString, User.class);
        System.out.println("New User signup received by Doc Collection consumer for userId: " + user.getId());
        try {
            driverOnboardingService.initDocumentCollection(user);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
