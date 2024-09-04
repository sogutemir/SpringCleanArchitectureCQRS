package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.helper.PaymentHelper;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.dataaccess.adapter.PaymentPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.PaymentDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.event.PaymentEventDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper.PaymentMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.adapter.UserPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class PaymentCreateUseCase {

    private final PaymentHelper paymentHelper;
    private final PaymentPersistenceAdapter paymentPersistenceAdapter;
    private final UserPersistenceAdapter userPersistenceAdapter;
    private final OrderPersistenceAdapter orderPersistenceAdapter;

    public PaymentCreateUseCase(PaymentHelper paymentHelper, PaymentPersistenceAdapter paymentPersistenceAdapter,
                                UserPersistenceAdapter userPersistenceAdapter, OrderPersistenceAdapter orderPersistenceAdapter) {
        this.paymentHelper = paymentHelper;
        this.paymentPersistenceAdapter = paymentPersistenceAdapter;
        this.userPersistenceAdapter = userPersistenceAdapter;
        this.orderPersistenceAdapter = orderPersistenceAdapter;
    }

    public PaymentEventDTO execute(PaymentDTO paymentDTO) {
        log.info("Payment create use case started. PaymentDTO: {}", paymentDTO);

        User user = userPersistenceAdapter.findById(paymentDTO.getUserId()).orElseThrow();
        Order order = orderPersistenceAdapter.findById(paymentDTO.getOrderId()).orElseThrow();

        BigDecimal totalAmount = order.getTotalAmount();

        if (paymentHelper.validateFunds(user, totalAmount)) {
            paymentHelper.updateOrderStatus(order, false);
            orderPersistenceAdapter.save(order);
            return new PaymentEventDTO(false, "Insufficient balance", null);
        }
        else{
            Payment payment = PaymentMapper.toEntity(paymentDTO);
            payment.setAmount(totalAmount);
            paymentPersistenceAdapter.save(payment);

            paymentHelper.updateUserBalance(user, totalAmount);
            paymentHelper.updateOrderStatus(order, true);

            userPersistenceAdapter.save(user);
            orderPersistenceAdapter.save(order);

            log.info("Payment completed successfully. Order id: {}", paymentDTO.getOrderId());

            return new PaymentEventDTO(true, "Payment completed successfully.", PaymentMapper.toDTO(payment));
        }
    }
}
