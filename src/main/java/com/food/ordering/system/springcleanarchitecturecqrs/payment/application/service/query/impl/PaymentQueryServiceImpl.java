package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.service.query.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.service.query.PaymentQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.query.PaymentFindByUserIdUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.query.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentFindByUserIdUseCase paymentFindByUserIdUseCase;

    @Override
    public List<PaymentResponseDto> findByUserId(Long userId) {
        return paymentFindByUserIdUseCase.execute(userId);
    }
}