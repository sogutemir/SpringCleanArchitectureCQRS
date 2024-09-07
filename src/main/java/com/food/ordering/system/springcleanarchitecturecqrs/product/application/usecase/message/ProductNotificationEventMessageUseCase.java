package com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.message;

import com.food.ordering.system.springcleanarchitecturecqrs.product.application.event.producer.ProductNotificationEventProducer;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.dto.event.ProductNotificationEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductNotificationEventMessageUseCase {

    private final ProductNotificationEventProducer productNotificationEventProducer;

    public ProductNotificationEventMessageUseCase(ProductNotificationEventProducer productNotificationEventProducer) {
        this.productNotificationEventProducer = productNotificationEventProducer;
    }

    public void execute(ProductNotificationEventDto productNotificationEventDto) {
        log.info("Executing ProductEventMessageUseCase for ProductEvent: {}", productNotificationEventDto);
        productNotificationEventProducer.sendMessage(productNotificationEventDto);
        log.info("ProductEventMessageUseCase execution completed.");
    }
}
