package com.food.ordering.system.springcleanarchitecturecqrs.payment.dataaccess.repository.query;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentQueryRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.user.id = :userId")
    List<Payment> findByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Payment p WHERE p.amount >= :amount")
    List<Payment> findByAmountGreaterThanEqual(@Param("amount") String amount);
}