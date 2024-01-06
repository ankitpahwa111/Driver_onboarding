package com.intuit.uber.onboarding.service.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DriverStatusProducer implements KafkaMessageProducer{
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public void sendMessageToTopic(String message) {
        log.info("DriverStatusProducer.sendMessageToTopic - Topic - {}, message - {}",
                "DriverStatusTopic", message);
        kafkaTemplate.send("DriverStatusTopic", message);
    }
}
