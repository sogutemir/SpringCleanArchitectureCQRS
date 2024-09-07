package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.config.KafkaConfig;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.helper.KafkaCallbackHelper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler.KafkaProducerExceptionHandler;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.event.PaymentCreatedEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaConfig kafkaConfig;
    private final KafkaCallbackHelper kafkaCallbackHelper;
    private final KafkaProducerExceptionHandler kafkaProducerExceptionHandler;

    public PaymentEventProducer(KafkaTemplate<String, Object> kafkaTemplate,
                                ObjectMapper objectMapper, KafkaConfig kafkaConfig,
                                KafkaCallbackHelper kafkaCallbackHelper,
                                KafkaProducerExceptionHandler kafkaProducerExceptionHandler) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.kafkaConfig = kafkaConfig;
        this.kafkaCallbackHelper = kafkaCallbackHelper;
        this.kafkaProducerExceptionHandler = kafkaProducerExceptionHandler;
    }

    public void sendMessage(PaymentCreatedEventDto paymentCreatedEventDto) {
        try {
            log.info("Sending PaymentEvent event: {}", paymentCreatedEventDto);
            String paymentEventJson = objectMapper.writeValueAsString(paymentCreatedEventDto);
            kafkaCallbackHelper.sendMessage(kafkaTemplate, kafkaConfig.getPaymentCreateTopic(),
                    paymentEventJson, paymentCreatedEventDto.getPaymentDTO().getOrderId().toString());
        } catch (JsonProcessingException e) {
            kafkaProducerExceptionHandler.handleSerializationException(e);
        } catch (Exception e) {
            kafkaProducerExceptionHandler.handleSendException(e);
        }
    }
}