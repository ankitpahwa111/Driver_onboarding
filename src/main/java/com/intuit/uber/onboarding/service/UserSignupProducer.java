package com.intuit.uber.onboarding.service;

import com.intuit.uber.onboarding.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserSignupProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessageToTopic(String user) {
        System.out.println("Ankit - sending user - " + user);
        kafkaTemplate.send("NewUserTopic", user);
    }

}
