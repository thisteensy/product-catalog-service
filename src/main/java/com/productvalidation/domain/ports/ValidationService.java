package com.productvalidation.domain.ports;

import com.productvalidation.domain.model.Product;
import com.productvalidation.domain.model.ValidationResult;

public interface ValidationService {
    ValidationResult validate(Product product);
}
