package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.helper.PaymentHelper;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.dataaccess.adapter.PaymentPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.PaymentDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.event.PaymentEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper.PaymentDtoToPaymentEventMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper.PaymentMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.adapter.UserPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Component
@Transactional
public class PaymentCreateUseCase {

    private final PaymentHelper paymentHelper;
    private final PaymentPersistenceAdapter paymentPersistenceAdapter;
    private final UserPersistenceAdapter userPersistenceAdapter;
    private final OrderPersistenceAdapter orderPersistenceAdapter;
    private final PaymentCreateMessageUseCase paymentCreateMessageUseCase;

    public PaymentCreateUseCase(PaymentHelper paymentHelper, PaymentPersistenceAdapter paymentPersistenceAdapter,
                                UserPersistenceAdapter userPersistenceAdapter, OrderPersistenceAdapter orderPersistenceAdapter, PaymentCreateMessageUseCase paymentCreateMessageUseCase) {
        this.paymentHelper = paymentHelper;
        this.paymentPersistenceAdapter = paymentPersistenceAdapter;
        this.userPersistenceAdapter = userPersistenceAdapter;
        this.orderPersistenceAdapter = orderPersistenceAdapter;
        this.paymentCreateMessageUseCase = paymentCreateMessageUseCase;
    }

    public PaymentEvent execute(PaymentDto paymentDTO) {
        log.info("Payment create use case started. PaymentDTO: {}", paymentDTO);

        User user = userPersistenceAdapter.findById(paymentDTO.getUserId()).orElseThrow();
        Order order = orderPersistenceAdapter.findById(paymentDTO.getOrderId()).orElseThrow();

        BigDecimal totalAmount = order.getTotalAmount();

        if (paymentHelper.validateFunds(user, totalAmount)) {
            paymentHelper.updateOrderStatus(order, false);
            orderPersistenceAdapter.save(order);
            log.warn("Insufficient balance. Payment failed. Order id: {}", paymentDTO.getOrderId());
            return new PaymentEvent(false, "Insufficient balance", null);
        }
        else{
            Payment payment = PaymentMapper.toEntity(paymentDTO);
            payment.setAmount(totalAmount);
            paymentPersistenceAdapter.save(payment);

            paymentHelper.updateUserBalance(user, totalAmount);
            paymentHelper.updateOrderStatus(order, true);

            userPersistenceAdapter.save(user);
            orderPersistenceAdapter.save(order);

            paymentCreateMessageUseCase.execute(PaymentDtoToPaymentEventMapper.toEvent(paymentDTO));

            log.info("Payment completed successfully. Order id: {}", paymentDTO.getOrderId());

            return new PaymentEvent(true, "Payment completed successfully.", PaymentMapper.toDTO(payment));
        }
    }
}
