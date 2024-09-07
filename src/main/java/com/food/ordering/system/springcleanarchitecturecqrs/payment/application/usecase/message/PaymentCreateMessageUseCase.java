package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.message;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.producer.PaymentEventProducer;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.event.PaymentCreatedEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentCreateMessageUseCase {

    private final PaymentEventProducer paymentEventProducer;

    public PaymentCreateMessageUseCase(PaymentEventProducer paymentEventProducer) {
        this.paymentEventProducer = paymentEventProducer;
    }

    public void execute(PaymentCreatedEventDto paymentCreatedEventDto) {
        log.info("Payment create message use case started. PaymentEventDTO: {}", paymentCreatedEventDto);
        try {
            paymentEventProducer.sendMessage(paymentCreatedEventDto);
        } catch (Exception e) {
            log.error("Failed to send payment event message", e);
            throw new RuntimeException("Failed to send payment event message", e);
        }
    }
}
