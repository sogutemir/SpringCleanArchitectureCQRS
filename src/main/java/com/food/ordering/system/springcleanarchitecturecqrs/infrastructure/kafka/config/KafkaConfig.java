package com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.topic.order}")
    private String orderTopic;

    @Value("${spring.kafka.consumer.group-id}")
    private String paymentGroup;

    @Value("${spring.kafka.topic.order-update}")
    private String orderUpdateTopic;

}