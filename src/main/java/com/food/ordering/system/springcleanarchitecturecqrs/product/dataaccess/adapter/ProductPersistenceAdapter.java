package com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.adapter;

import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.repository.crud.ProductCrudRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.repository.query.ProductQueryRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductPersistenceAdapter {

    private final ProductCrudRepository productCrudRepository;
    private final ProductQueryRepository productQueryRepository;

    public ProductPersistenceAdapter(ProductCrudRepository productCrudRepository, ProductQueryRepository productQueryRepository) {
        this.productCrudRepository = productCrudRepository;
        this.productQueryRepository = productQueryRepository;
    }

    public Optional<Product> findById(Long id) {
        return productQueryRepository.findById(id);
    }


    public List<Product> findAllByIds(List<Long> ids) {
        return productQueryRepository.findAllById(ids);
    }

    public List<Product> findByNameContaining(String name) {
        return productQueryRepository.findByNameContaining(name);
    }

    public List<Product> findByProductStatus(String status) {
        return productQueryRepository.findByProductStatus(status);
    }

    public Product save(Product product) {
        return productCrudRepository.save(product);
    }

    public void deleteById(Long id) {
        productCrudRepository.deleteById(id);
    }
}