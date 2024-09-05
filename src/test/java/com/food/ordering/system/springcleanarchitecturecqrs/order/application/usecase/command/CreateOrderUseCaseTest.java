package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDto;
import com.food.ordering.system.springcleanarchitecturecqrs.common.application.service.ProductValidationService;
import com.food.ordering.system.springcleanarchitecturecqrs.common.application.service.UserValidationService;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.helper.OrderCalculationHelper;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception.InsufficientStockException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception.ProductNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.repository.query.ProductQueryRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.repository.query.UserQueryRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import({CreateOrderUseCase.class, UserValidationService.class, ProductValidationService.class, OrderCalculationHelper.class, OrderPersistenceAdapter.class})
public class CreateOrderUseCaseTest {

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Autowired
    private OrderPersistenceAdapter orderPersistenceAdapter;

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

    private User testUser;
    private Product testProduct1;
    private Product testProduct2;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .name("Test User")
                .email("testuser@example.com")
                .money(BigDecimal.valueOf(1000))
                .build();
        userQueryRepository.save(testUser);

        testProduct1 = Product.builder()
                .name("Product 1")
                .description("Description 1")
                .price(BigDecimal.valueOf(100))
                .stockQuantity(10)
                .build();
        productQueryRepository.save(testProduct1);

        testProduct2 = Product.builder()
                .name("Product 2")
                .description("Description 2")
                .price(BigDecimal.valueOf(200))
                .stockQuantity(5)
                .build();
        productQueryRepository.save(testProduct2);
    }

    @Test
    void testCreateOrder_Success() {
        OrderDto orderDTO = OrderDto.builder()
                .userId(testUser.getId())
                .build();

        Map<String, String> productIdQuantityMap = new HashMap<>();
        productIdQuantityMap.put(testProduct1.getId().toString(), "2");
        productIdQuantityMap.put(testProduct2.getId().toString(), "1");

        OrderDto createdOrder = createOrderUseCase.execute(orderDTO, productIdQuantityMap);

        assertNotNull(createdOrder);
        assertEquals(2, createdOrder.getProductIds().size());
        assertEquals(BigDecimal.valueOf(400), createdOrder.getTotalAmount());
    }

    @Test
    void testCreateOrder_ProductNotFound() {
        OrderDto orderDTO = OrderDto.builder()
                .userId(testUser.getId())
                .build();

        Map<String, String> productIdQuantityMap = new HashMap<>();
        productIdQuantityMap.put("999", "2");

        assertThrows(ProductNotFoundException.class, () -> createOrderUseCase.execute(orderDTO, productIdQuantityMap));
    }

    @Test
    void testCreateOrder_InsufficientStock() {
        OrderDto orderDTO = OrderDto.builder()
                .userId(testUser.getId())
                .build();

        Map<String, String> productIdQuantityMap = new HashMap<>();
        productIdQuantityMap.put(testProduct1.getId().toString(), "11");

        assertThrows(InsufficientStockException.class, () -> createOrderUseCase.execute(orderDTO, productIdQuantityMap));
    }
}
