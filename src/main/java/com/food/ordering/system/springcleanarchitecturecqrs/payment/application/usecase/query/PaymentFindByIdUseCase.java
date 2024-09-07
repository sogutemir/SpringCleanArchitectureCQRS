package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.query;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.dataaccess.adapter.PaymentPersistenceAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentFindByIdUseCase {

    private final PaymentPersistenceAdapter paymentPersistenceAdapter;

    public PaymentFindByIdUseCase(PaymentPersistenceAdapter paymentPersistenceAdapter) {
        this.paymentPersistenceAdapter = paymentPersistenceAdapter;
    }

    public boolean execute(Long paymentId) {
        log.info("Checking existence of payment with id: {}", paymentId);
        return paymentPersistenceAdapter.findById(paymentId).isPresent();
    }
}
