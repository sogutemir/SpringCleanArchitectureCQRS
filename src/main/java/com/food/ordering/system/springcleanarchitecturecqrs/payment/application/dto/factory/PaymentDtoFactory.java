package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.factory;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.dto.OrderEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.crud.PaymentDto;

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