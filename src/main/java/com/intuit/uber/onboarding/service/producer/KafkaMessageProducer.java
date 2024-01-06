package com.intuit.uber.onboarding.service.producer;

public interface KafkaMessageProducer {
    public void sendMessageToTopic(String message);
}
