package com.productvalidation.domain.ports;

import com.productvalidation.domain.model.Product;

public interface EventProducer {
    void produce(Product product);
}