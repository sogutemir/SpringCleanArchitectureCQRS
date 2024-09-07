package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.crud.OrderDto;
import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.repository.query.OrderQueryRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.repository.query.ProductQueryRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.repository.query.UserQueryRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CreateOrderIntegrationTest {

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Autowired
    private OrderQueryRepository orderQueryRepository;

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

    private User testUser;
    private Product testProduct;

    @BeforeEach
    void setup() {
        testUser = User.builder()
                .name("Test User")
                .email("test@example.com")
                .money(BigDecimal.valueOf(1000))
                .build();
        userQueryRepository.save(testUser);

        testProduct = Product.builder()
                .name("Test Product")
                .price(BigDecimal.valueOf(100))
                .stockQuantity(10)
                .build();
        productQueryRepository.save(testProduct);
    }


    @Test
    void createOrder_Successful() {
        Map<Long, Integer> productQuantities = new HashMap<>();
        productQuantities.put(testProduct.getId(), 2);

        OrderDto orderDto = OrderDto.builder()
                .userId(testUser.getId())
                .productQuantities(productQuantities)
                .totalAmount(BigDecimal.valueOf(200))
                .build();

        OrderDto createdOrder = createOrderUseCase.execute(orderDto, productQuantities);

        assertNotNull(createdOrder);
        assertEquals(testUser.getId(), createdOrder.getUserId());
        assertEquals(BigDecimal.valueOf(200), createdOrder.getTotalAmount());

        List<Order> orders = orderQueryRepository.findByUserId(testUser.getId());
        assertFalse(orders.isEmpty());

        Order order = orders.get(0);
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
        assertEquals(1, order.getProducts().size());
    }

    @Test
    void createOrder_Failure_InsufficientStock() {
        testProduct.setStockQuantity(1);
        productQueryRepository.save(testProduct);

        Map<Long, Integer> productQuantities = new HashMap<>();
        productQuantities.put(testProduct.getId(), 2);

        OrderDto orderDto = OrderDto.builder()
                .userId(testUser.getId())
                .productQuantities(productQuantities)
                .totalAmount(BigDecimal.valueOf(200))
                .build();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            createOrderUseCase.execute(orderDto, productQuantities);
        });

        assertTrue(exception.getMessage().contains("Insufficient stock"));
    }

}

