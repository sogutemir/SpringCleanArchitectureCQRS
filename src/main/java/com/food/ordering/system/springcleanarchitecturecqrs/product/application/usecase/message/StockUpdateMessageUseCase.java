package com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.message;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.producer.StockUpdateEventProducer;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.dto.event.StockUpdateEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockUpdateMessageUseCase {

    private final StockUpdateEventProducer stockUpdateEventProducer;

    public StockUpdateMessageUseCase(StockUpdateEventProducer stockUpdateEventProducer) {
        this.stockUpdateEventProducer = stockUpdateEventProducer;
    }

    public void execute(StockUpdateEventDto stockUpdateEventDto) {
        log.info("Executing StockUpdateMessageUseCase for StockUpdateEvent: {}", stockUpdateEventDto);
        stockUpdateEventProducer.sendMessage(stockUpdateEventDto);
        log.info("StockUpdateMessageUseCase execution completed.");
    }
}
