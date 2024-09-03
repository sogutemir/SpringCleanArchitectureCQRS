package com.food.ordering.system.springcleanarchitecturecqrs.payment.dataaccess.adapter;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.dataaccess.repository.crud.PaymentCrudRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.dataaccess.repository.query.PaymentQueryRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PaymentPersistenceAdapter {

    private final PaymentCrudRepository paymentCrudRepository;
    private final PaymentQueryRepository paymentQueryRepository;

    public PaymentPersistenceAdapter(PaymentCrudRepository paymentCrudRepository, PaymentQueryRepository paymentQueryRepository) {
        this.paymentCrudRepository = paymentCrudRepository;
        this.paymentQueryRepository = paymentQueryRepository;
    }

    public Optional<Payment> findById(Long id) {
        return paymentQueryRepository.findById(id);
    }

    public List<Payment> findByUserId(Long userId) {
        return paymentQueryRepository.findByUserId(userId);
    }

    public List<Payment> findByAmountGreaterThanEqual(String amount) {
        return paymentQueryRepository.findByAmountGreaterThanEqual(amount);
    }

    public Payment save(Payment payment) {
        return paymentCrudRepository.save(payment);
    }

    public void deleteById(Long id) {
        paymentCrudRepository.deleteById(id);
    }
}