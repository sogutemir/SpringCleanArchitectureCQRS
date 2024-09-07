package com.food.ordering.system.springcleanarchitecturecqrs.product.api.query;

import com.food.ordering.system.springcleanarchitecturecqrs.product.application.service.query.ProductQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.query.ProductResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products/query")
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    public ProductQueryController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findProductById(@PathVariable Long id) {
        Optional<ProductResponseDto> product = productQueryService.findProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findProductsByName(@RequestParam String name) {
        List<ProductResponseDto> products = productQueryService.findProductsByName(name);
        return ResponseEntity.ok(products);
    }
}