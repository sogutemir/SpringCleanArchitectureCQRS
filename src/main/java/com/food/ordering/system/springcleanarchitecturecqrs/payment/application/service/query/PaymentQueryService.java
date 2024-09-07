package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.service.query;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.query.PaymentResponseDto;

import java.util.List;

public interface PaymentQueryService {
    List<PaymentResponseDto> findByUserId(Long userId);
}