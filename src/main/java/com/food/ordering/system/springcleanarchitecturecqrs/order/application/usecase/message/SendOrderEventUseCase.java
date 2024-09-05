package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.message;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.producer.OrderEventProducer;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendOrderEventUseCase {

    private final OrderEventProducer orderEventProducer;

    public SendOrderEventUseCase(OrderEventProducer orderEventProducer) {
        this.orderEventProducer = orderEventProducer;
    }

    public void execute(OrderEvent orderEvent) {
        try {
            log.info("Sending order event for order id: {}", orderEvent.getOrderId());
            orderEventProducer.sendOrderEvent(orderEvent);
            log.info("Order event sent successfully for order id: {}", orderEvent.getOrderId());
        } catch (Exception kafkaException) {
            log.error("Error occurred while sending order event for order id: {}. Error: {}", orderEvent.getOrderId(), kafkaException.getMessage(), kafkaException);
        }
    }
}
