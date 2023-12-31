package com.intuit.uber.onboarding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserSignupProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessageToTopic(String message) {
        kafkaTemplate.send("NewUserTopic", message);
    }

}
