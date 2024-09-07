package com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.message;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.producer.StockUpdateEventProducer;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.event.dto.StockUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockUpdateMessageUseCase {

    private final StockUpdateEventProducer stockUpdateEventProducer;

    public StockUpdateMessageUseCase(StockUpdateEventProducer stockUpdateEventProducer) {
        this.stockUpdateEventProducer = stockUpdateEventProducer;
    }

    public void execute(StockUpdateEvent stockUpdateEvent) {
        log.info("Executing StockUpdateMessageUseCase for StockUpdateEvent: {}", stockUpdateEvent);
        stockUpdateEventProducer.sendMessage(stockUpdateEvent);
        log.info("StockUpdateMessageUseCase execution completed.");
    }
}
