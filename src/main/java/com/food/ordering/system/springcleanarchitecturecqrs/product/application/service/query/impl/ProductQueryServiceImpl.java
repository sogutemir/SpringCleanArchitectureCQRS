package com.food.ordering.system.springcleanarchitecturecqrs.product.application.service.query.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.product.application.service.query.ProductQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.query.FindProductByIdUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.query.FindProductsByNameUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.dto.query.ProductResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<ProductResponseDto> findProductById(Long id) {
        return findProductByIdUseCase.execute(id);
    }

    @Override
    public List<ProductResponseDto> findProductsByName(String name) {
        return findProductsByNameUseCase.execute(name);
    }
}