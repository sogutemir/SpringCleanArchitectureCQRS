package com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.factory;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.event.OrderEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.crud.PaymentDto;

public class PaymentDtoFactory {

    public static PaymentDto createPaymentDto(OrderEvent orderEvent) {
        return PaymentDto.builder()
                .orderId(orderEvent.getOrderId())
                .userId(orderEvent.getUserId())
                .amount(orderEvent.getTotalAmount())
                .productQuantities(orderEvent.getProductQuantities())
                .build();
    }
}