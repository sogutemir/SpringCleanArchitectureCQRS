package com.food.ordering.system.springcleanarchitecturecqrs.payment.api.query;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.service.query.PaymentQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.query.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentQueryController {

    private final PaymentQueryService paymentQueryService;

    @GetMapping("/user/{userId}")
    public List<PaymentResponseDto> getPaymentsByUserId(@PathVariable Long userId) {
        return paymentQueryService.findByUserId(userId);
    }
}