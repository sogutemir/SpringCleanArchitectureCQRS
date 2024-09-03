package com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.repository.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductCrudRepository extends CrudRepository<Product, Long> {

}