package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.factory;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.event.OrderCreateEventDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.crud.PaymentDto;

public class PaymentDtoFactory {

    public static PaymentDto createPaymentDto(OrderCreateEventDto orderCreateEventDto) {
        return PaymentDto.builder()
                .orderId(orderCreateEventDto.getOrderId())
                .userId(orderCreateEventDto.getUserId())
                .amount(orderCreateEventDto.getTotalAmount())
                .productQuantities(orderCreateEventDto.getProductQuantities())
                .build();
    }
}