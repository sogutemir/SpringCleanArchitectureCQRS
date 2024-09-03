package com.food.ordering.system.springcleanarchitecturecqrs.product.application.service.command.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.product.application.service.command.ProductCommandService;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.crud.CreateProductUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.crud.DeleteProductUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.crud.UpdateProductUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    public ProductCommandServiceImpl(
            CreateProductUseCase createProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            DeleteProductUseCase deleteProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        log.info("Creating a new product with name: {}", productDTO.getName());
        return createProductUseCase.execute(productDTO);
    }

    @Override
    public Optional<ProductDTO> updateProduct(Long id, ProductDTO productDTO) {
        log.info("Updating product with id: {}", id);
        return updateProductUseCase.execute(id, productDTO);
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        deleteProductUseCase.execute(id);
    }
}