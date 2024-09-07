package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.message.SendOrderUpdateEventUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.query.FindOrderByIdUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.enums.OrderStatus;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.mapper.OrderUpdateEventToOrderMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.helper.PaymentHelper;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.message.PaymentCreateMessageUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.dataaccess.adapter.PaymentPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.crud.PaymentDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.event.PaymentEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper.PaymentDtoToPaymentEventMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper.PaymentMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.message.StockUpdateMessageUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.event.StockUpdateEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.message.SendUserUpdateEventUseCase;
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
    private final PaymentCreateMessageUseCase paymentCreateMessageUseCase;
    private final StockUpdateMessageUseCase stockUpdateMessageUseCase;
    private final SendUserUpdateEventUseCase sendUserUpdateEventUseCase;
    private final SendOrderUpdateEventUseCase sendOrderUpdateEventUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindOrderByIdUseCase findOrderByIdUseCase;

    public PaymentCreateUseCase(PaymentHelper paymentHelper,
                                PaymentPersistenceAdapter paymentPersistenceAdapter,
                                PaymentCreateMessageUseCase paymentCreateMessageUseCase,
                                StockUpdateMessageUseCase stockUpdateMessageUseCase,
                                SendUserUpdateEventUseCase userUpdateEventProducer, SendOrderUpdateEventUseCase sendOrderUpdateEventUseCase,
                                FindUserByIdUseCase findUserByIdUseCase, FindOrderByIdUseCase findOrderByIdUseCase) {
        this.paymentHelper = paymentHelper;
        this.paymentPersistenceAdapter = paymentPersistenceAdapter;
        this.paymentCreateMessageUseCase = paymentCreateMessageUseCase;
        this.stockUpdateMessageUseCase = stockUpdateMessageUseCase;
        this.sendUserUpdateEventUseCase = userUpdateEventProducer;
        this.sendOrderUpdateEventUseCase = sendOrderUpdateEventUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.findOrderByIdUseCase = findOrderByIdUseCase;
    }

    public PaymentEvent execute(PaymentDto paymentDTO) {
        log.info("Payment create use case started. PaymentDTO: {}", paymentDTO);

        User user = findUserByIdUseCase.findUserEntityById(paymentDTO.getUserId());
        Order order = findOrderByIdUseCase.findUserEntityById(paymentDTO.getOrderId());

        BigDecimal totalAmount = order.getTotalAmount();

        if (paymentHelper.validateFunds(user, totalAmount)) {
            paymentHelper.updateOrderStatus(order, false);
            sendOrderUpdateEventUseCase.execute(OrderUpdateEventToOrderMapper.toOrderUpdateEvent(order, OrderStatus.CANCELLED));
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

            sendUserUpdateEventUseCase.execute(UserUpdateEventToUserMapper.toUserUpdateEvent(user,order.getId()));
            sendOrderUpdateEventUseCase.execute(OrderUpdateEventToOrderMapper.toOrderUpdateEvent(order, OrderStatus.APPROVED));


            StockUpdateEvent stockUpdateEvent = new StockUpdateEvent(order.getId(), paymentDTO.getProductQuantities());
            stockUpdateMessageUseCase.execute(stockUpdateEvent);

            paymentCreateMessageUseCase.execute(PaymentDtoToPaymentEventMapper.toEvent(paymentDTO));

            log.info("Payment completed successfully. Order id: {}", paymentDTO.getOrderId());

            return new PaymentEvent(true, "Payment completed successfully.", PaymentMapper.toDTO(payment));
        }
    }

}