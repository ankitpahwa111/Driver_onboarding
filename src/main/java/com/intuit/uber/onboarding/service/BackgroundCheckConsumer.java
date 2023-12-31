package com.intuit.uber.onboarding.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BackgroundCheckConsumer {
    @KafkaListener(topics = "NewUserTopic", groupId = "bgc-group")
    public void listenToCodeDecodeKafkaTopic(String messageReceived) {
        System.out.println("Message received by BGC consumer is " + messageReceived);
    }
}
