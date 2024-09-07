package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.message;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.producer.OrderUpdateEventProducer;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.dto.OrderUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendOrderUpdateEventUseCase {

    private final OrderUpdateEventProducer orderUpdateEventProducer;

    public SendOrderUpdateEventUseCase(OrderUpdateEventProducer orderUpdateEventProducer) {
        this.orderUpdateEventProducer = orderUpdateEventProducer;
    }

    public void execute(OrderUpdateEvent orderUpdateEvent) {
        try {
            log.info("Sending order event for order id: {}", orderUpdateEvent.getOrderId());
            orderUpdateEventProducer.sendOrderUpdateEvent(orderUpdateEvent);
            log.info("Order event sent successfully for order id: {}", orderUpdateEvent.getOrderId());
        } catch (Exception kafkaException) {
            log.error("Error occurred while sending order event for order id: {}. Error: {}", orderUpdateEvent.getOrderId(), kafkaException.getMessage(), kafkaException);
        }
    }

}
