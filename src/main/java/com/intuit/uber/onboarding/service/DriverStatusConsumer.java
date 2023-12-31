package com.intuit.uber.onboarding.service;

import com.google.gson.Gson;
import com.intuit.uber.onboarding.model.entity.DriverStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DriverStatusConsumer {
    Gson gson = new Gson();

    @KafkaListener(topics = "DriverStatusTopic", groupId = "driverIsOnlineStatus-group")
    public void listenToDriverStatusKafkaTopic(String driverStatusString) {
        DriverStatus driverStatus = gson.fromJson(driverStatusString, DriverStatus.class);
        System.out.println("Driver online status changed for userId: " + driverStatus.getUserId() + " to: " + driverStatus.getIsOnline());
    }
}
