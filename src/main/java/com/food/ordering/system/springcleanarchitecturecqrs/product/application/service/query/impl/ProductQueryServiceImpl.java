package com.food.ordering.system.springcleanarchitecturecqrs.product.application.service.query.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.product.application.service.query.ProductQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.query.FindProductByIdUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.query.FindProductsByNameUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.ProductResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final FindProductByIdUseCase findProductByIdUseCase;
    private final FindProductsByNameUseCase findProductsByNameUseCase;

    public ProductQueryServiceImpl(
            FindProductByIdUseCase findProductByIdUseCase,
            FindProductsByNameUseCase findProductsByNameUseCase) {
        this.findProductByIdUseCase = findProductByIdUseCase;
        this.findProductsByNameUseCase = findProductsByNameUseCase;
    }

    @Override
    public Optional<ProductResponseDTO> findProductById(Long id) {
        log.info("Finding product with id: {}", id);
        return findProductByIdUseCase.execute(id);
    }

    @Override
    public List<ProductResponseDTO> findProductsByName(String name) {
        log.info("Finding products with name containing: {}", name);
        return findProductsByNameUseCase.execute(name);
    }
}