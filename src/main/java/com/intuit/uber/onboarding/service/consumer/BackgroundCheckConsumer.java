package com.intuit.uber.onboarding.service.consumer;

import com.google.gson.Gson;
import com.intuit.uber.onboarding.model.entity.User;
import com.intuit.uber.onboarding.service.DriverOnboardingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BackgroundCheckConsumer {

    @Autowired
    private DriverOnboardingService driverOnboardingService;

    Gson gson = new Gson();
    @KafkaListener(topics = "NewUserTopic", groupId = "bgc-group")
    public void listenKafkaTopic(String userString) {
        User user = gson.fromJson(userString, User.class);
        log.info("BackgroundCheckConsumer.listenToDriverStatusKafkaTopic - Received message - {} in topic - {}",
                userString, "NewUserTopic");
        try {
            driverOnboardingService.initBGC(user);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
