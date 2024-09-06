package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.helper.PaymentHelper;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.message.PaymentCreateMessageUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.dataaccess.adapter.PaymentPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.PaymentDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.event.PaymentEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper.PaymentDtoToPaymentEventMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper.PaymentMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.message.StockUpdateMessageUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.event.StockUpdateEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.message.UserUpdateEventUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.query.FindUserByIdUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.mapper.UserUpdateEventToUserMapper;
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
    private final OrderPersistenceAdapter orderPersistenceAdapter;
    private final PaymentCreateMessageUseCase paymentCreateMessageUseCase;
    private final StockUpdateMessageUseCase stockUpdateMessageUseCase;
    private final UserUpdateEventUseCase userUpdateEventUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;

    public PaymentCreateUseCase(PaymentHelper paymentHelper,
                                PaymentPersistenceAdapter paymentPersistenceAdapter,
                                OrderPersistenceAdapter orderPersistenceAdapter,
                                PaymentCreateMessageUseCase paymentCreateMessageUseCase,
                                StockUpdateMessageUseCase stockUpdateMessageUseCase,
                                UserUpdateEventUseCase userUpdateEventProducer,
                                FindUserByIdUseCase findUserByIdUseCase) {
        this.paymentHelper = paymentHelper;
        this.paymentPersistenceAdapter = paymentPersistenceAdapter;
        this.orderPersistenceAdapter = orderPersistenceAdapter;
        this.paymentCreateMessageUseCase = paymentCreateMessageUseCase;
        this.stockUpdateMessageUseCase = stockUpdateMessageUseCase;
        this.userUpdateEventUseCase = userUpdateEventProducer;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }

    public PaymentEvent execute(PaymentDto paymentDTO) {
        log.info("Payment create use case started. PaymentDTO: {}", paymentDTO);

        User user = findUserByIdUseCase.findUserEntityById(paymentDTO.getUserId());
        Order order = orderPersistenceAdapter.findById(paymentDTO.getOrderId()).orElseThrow();

        BigDecimal totalAmount = order.getTotalAmount();

        if (paymentHelper.validateFunds(user, totalAmount)) {
            paymentHelper.updateOrderStatus(order, false);
            orderPersistenceAdapter.save(order);
            paymentCreateMessageUseCase.execute(PaymentDtoToPaymentEventMapper.toEvent(paymentDTO));
            log.warn("Insufficient balance. Payment failed. Order id: {}", paymentDTO.getOrderId());
            return new PaymentEvent(false, "Insufficient balance", null);
        } else {
            Payment payment = PaymentMapper.toEntity(paymentDTO);
            payment.setAmount(totalAmount);
            paymentPersistenceAdapter.save(payment);

            paymentDTO.setPaymentId(payment.getId());

            paymentHelper.updateUserBalance(user, totalAmount);
            paymentHelper.updateOrderStatus(order, true);

            userUpdateEventUseCase.execute(UserUpdateEventToUserMapper.toUserUpdateEvent(user,order.getId()));
            orderPersistenceAdapter.save(order);

            StockUpdateEvent stockUpdateEvent = new StockUpdateEvent(order.getId(), paymentDTO.getProductQuantities());
            stockUpdateMessageUseCase.execute(stockUpdateEvent);

            paymentCreateMessageUseCase.execute(PaymentDtoToPaymentEventMapper.toEvent(paymentDTO));

            log.info("Payment completed successfully. Order id: {}", paymentDTO.getOrderId());

            return new PaymentEvent(true, "Payment completed successfully.", PaymentMapper.toDTO(payment));
        }
    }

}