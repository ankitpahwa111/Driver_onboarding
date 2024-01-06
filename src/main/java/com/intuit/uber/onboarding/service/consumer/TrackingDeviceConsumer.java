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
public class TrackingDeviceConsumer {
    @Autowired
    private DriverOnboardingService driverOnboardingService;

    Gson gson = new Gson();
    @KafkaListener(topics = "NewUserTopic", groupId = "trackingDevice-group")
    public void listenToKafkaTopic(String userString) {
        User user = gson.fromJson(userString, User.class);
        log.info("BackgroundCheckConsumer.listenToDriverStatusKafkaTopic - Received message - {} in topic - {}",
                userString, "NewUserTopic");
        try {
            driverOnboardingService.initDeviceShipment(user);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
