package com.food.ordering.system.springcleanarchitecturecqrs.payment.dataaccess.repository.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentCrudRepository extends CrudRepository<Payment, Long> {

}