package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.crud.OrderDto;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.validation.ProductValidationUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.helper.OrderCalculationHelper;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.mapper.OrderMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.repository.query.ProductQueryRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.repository.query.UserQueryRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import({UpdateOrderUseCase.class, ProductValidationUseCase.class, OrderCalculationHelper.class, OrderPersistenceAdapter.class})
public class UpdateOrderUseCaseTest {

    @Autowired
    private UpdateOrderUseCase updateOrderUseCase;

    @Autowired
    private OrderPersistenceAdapter orderPersistenceAdapter;

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

    private User testUser;
    private Product testProduct1;

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

        Product testProduct2 = Product.builder()
                .name("Product 2")
                .description("Description 2")
                .price(BigDecimal.valueOf(200))
                .stockQuantity(5)
                .build();
        productQueryRepository.save(testProduct2);

        OrderDto orderDTO = OrderDto.builder()
                .userId(testUser.getId())
                .build();

        Map<Long, Integer> productIdQuantityMap = new HashMap<>();
        productIdQuantityMap.put(testProduct1.getId(), 2);
        orderPersistenceAdapter.save(OrderMapper.toEntity(orderDTO, productIdQuantityMap, BigDecimal.valueOf(200)));
    }
//
//    @Test
//    void testUpdateOrder_Success() {
//        OrderDto orderDTO = OrderDto.builder()
//                .userId(testUser.getId())
//                .build();
//
//        Map<Long, Integer> productIdQuantityMap = new HashMap<>();
//        productIdQuantityMap.put(testProduct1.getId(), 2);
//
//        OrderDto updatedOrder = updateOrderUseCase.execute(1L, orderDTO, productIdQuantityMap).orElse(null);
//
//        assertNotNull(updatedOrder);
//        assertEquals(BigDecimal.valueOf(200), updatedOrder.getTotalAmount());
//    }
//
//    @Test
//    void testUpdateOrder_OrderNotFound() {
//        OrderDto orderDTO = OrderDto.builder()
//                .userId(testUser.getId())
//                .build();
//
//        Map<Long, Integer> productIdQuantityMap = new HashMap<>();
//        productIdQuantityMap.put(testProduct1.getId(), 2);
//
//        assertThrows(OrderNotFoundException.class, () -> updateOrderUseCase.execute(999L, orderDTO, productIdQuantityMap));
//    }
//
//    @Test
//    void testUpdateOrder_ProductNotFound() {
//        OrderDto orderDTO = OrderDto.builder()
//                .userId(testUser.getId())
//                .build();
//
//        Map<Long, Integer> productIdQuantityMap = new HashMap<>();
//        productIdQuantityMap.put(999L, 2);
//
//        assertThrows(ProductNotFoundException.class, () -> updateOrderUseCase.execute(1L, orderDTO, productIdQuantityMap));
//    }
//
//    @Test
//    void testUpdateOrder_InsufficientStock() {
//        OrderDto orderDTO = OrderDto.builder()
//                .userId(testUser.getId())
//                .build();
//
//        Map<Long, Integer> productIdQuantityMap = new HashMap<>();
//        productIdQuantityMap.put(testProduct1.getId(), 11);
//
//        assertThrows(InsufficientStockException.class, () -> updateOrderUseCase.execute(1L, orderDTO, productIdQuantityMap));
//    }
}
