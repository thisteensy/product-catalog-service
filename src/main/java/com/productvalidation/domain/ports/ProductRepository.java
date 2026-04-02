package com.productvalidation.domain.ports;

import com.productvalidation.domain.model.Product;
import com.productvalidation.domain.model.ProductStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(UUID id);
    List<Product> findByStatus(ProductStatus status);
    void deleteById(UUID id);
}