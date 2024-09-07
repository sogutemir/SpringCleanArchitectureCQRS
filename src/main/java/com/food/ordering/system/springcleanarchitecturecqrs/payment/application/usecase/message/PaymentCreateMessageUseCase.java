package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.message;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.producer.PaymentEventProducer;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.dto.PaymentCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentCreateMessageUseCase {

    private final PaymentEventProducer paymentEventProducer;

    public PaymentCreateMessageUseCase(PaymentEventProducer paymentEventProducer) {
        this.paymentEventProducer = paymentEventProducer;
    }

    public void execute(PaymentCreatedEvent paymentCreatedEvent) {
        log.info("Payment create message use case started. PaymentEventDTO: {}", paymentCreatedEvent);
        try {
            paymentEventProducer.sendMessage(paymentCreatedEvent);
        } catch (Exception e) {
            log.error("Failed to send payment event message", e);
            throw new RuntimeException("Failed to send payment event message", e);
        }
    }
}
