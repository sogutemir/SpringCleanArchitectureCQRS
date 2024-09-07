package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.message;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.producer.OrderUpdateEventProducer;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.event.OrderUpdateEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendOrderUpdateEventUseCase {

    private final OrderUpdateEventProducer orderUpdateEventProducer;

    public SendOrderUpdateEventUseCase(OrderUpdateEventProducer orderUpdateEventProducer) {
        this.orderUpdateEventProducer = orderUpdateEventProducer;
    }

    public void execute(OrderUpdateEventDto orderUpdateEventDto) {
        try {
            log.info("Sending order event for order id: {}", orderUpdateEventDto.getOrderId());
            orderUpdateEventProducer.sendOrderUpdateEvent(orderUpdateEventDto);
            log.info("Order event sent successfully for order id: {}", orderUpdateEventDto.getOrderId());
        } catch (Exception kafkaException) {
            log.error("Error occurred while sending order event for order id: {}. Error: {}", orderUpdateEventDto.getOrderId(), kafkaException.getMessage(), kafkaException);
        }
    }

}
