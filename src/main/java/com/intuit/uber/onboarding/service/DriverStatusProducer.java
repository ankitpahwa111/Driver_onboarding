package com.intuit.uber.onboarding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DriverStatusProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessageToTopic(String message) {
        System.out.println("Ankit - sending message for driver status - " + message);
        kafkaTemplate.send("DriverStatusTopic", message);
    }
}
