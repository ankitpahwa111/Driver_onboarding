package com.intuit.uber.onboarding.service.consumer;

import com.google.gson.Gson;
import com.intuit.uber.onboarding.model.entity.DriverStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DriverStatusConsumer {
    Gson gson = new Gson();

    @KafkaListener(topics = "DriverStatusTopic", groupId = "driverIsOnlineStatus-group")
    public void listenToKafkaTopic(String driverStatusString) {
        log.info("DriverStatusConsumer.listenToDriverStatusKafkaTopic - Received message - {} in topic - {}",
                driverStatusString, "DriverStatusTopic");
        DriverStatus driverStatus = gson.fromJson(driverStatusString, DriverStatus.class);
        System.out.println("Driver online status changed for userId: " + driverStatus.getUserId() + " to: " + driverStatus.getIsOnline());
    }
}
