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

    @Value("${spring.kafka.topic.payment-create}")
    private String paymentCreateTopic;

    @Value("${spring.kafka.topic.user-update}")
    private String userUpdateTopic;

    @Value("${spring.kafka.topic.stock-update}")
    private String StockUpdateTopic;

    @Value("${spring.kafka.topic.product-notification}")
    private String ProductNotificationTopic;

    @Value("${spring.kafka.topic.order-update}")
    private String orderUpdateTopic;

    @Value("${spring.kafka.topic.producer-exception}")
    private String kafkaProducerExceptionTopic;

    @Value("${spring.kafka.topic.listener-exception}")
    private String kafkaListenerExceptionTopic;

    @Value("${spring.kafka.topic.dead-letter-queue}")
    private String deadLetterQueueTopic;

}