package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.query;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.dataaccess.adapter.PaymentPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.query.PaymentResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper.PaymentResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PaymentFindByUserIdUseCase {

    private final PaymentPersistenceAdapter paymentPersistenceAdapter;

    public PaymentFindByUserIdUseCase(PaymentPersistenceAdapter paymentPersistenceAdapter) {
        this.paymentPersistenceAdapter = paymentPersistenceAdapter;
    }

    public List<PaymentResponseDto> execute(Long userId) {
        try {
            log.info("Fetching payments for user ID: {}", userId);
            List<Payment> payments = paymentPersistenceAdapter.findByUserId(userId);
            return payments.stream()
                    .map(PaymentResponseMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error occurred while fetching payments for user ID: {}", userId, e);
            throw new RuntimeException("Failed to fetch payments for user ID: " + userId, e);
        }
    }
}