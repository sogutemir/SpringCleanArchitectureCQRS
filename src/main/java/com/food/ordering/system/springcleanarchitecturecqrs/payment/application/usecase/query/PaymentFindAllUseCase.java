package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.query;

import java.util.List;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.dataaccess.adapter.PaymentPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.query.PaymentResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper.PaymentResponseMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentFindAllUseCase {

    private final PaymentPersistenceAdapter paymentPersistenceAdapter;

    public PaymentFindAllUseCase(PaymentPersistenceAdapter paymentPersistenceAdapter) {
        this.paymentPersistenceAdapter = paymentPersistenceAdapter;
    }

    public List<PaymentResponseDto> execute() {
        try {
            log.info("Fetching all payments");
            List<Payment> payments = paymentPersistenceAdapter.findAll();
            return payments.stream()
                    .map(PaymentResponseMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error occurred while fetching payments", e);
            throw new RuntimeException("Failed to fetch payments", e);
        }
    }

}