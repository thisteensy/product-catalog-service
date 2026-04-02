package com.productvalidation.domain.service;

import com.productvalidation.domain.model.Product;
import com.productvalidation.domain.model.ValidationResult;
import com.productvalidation.domain.ports.RuleEngine;
import com.productvalidation.domain.ports.ValidationService;

public class ValidationServiceImpl implements ValidationService {

    private final RuleEngine ruleEngine;

    public ValidationServiceImpl(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    @Override
    public ValidationResult validate(Product product) {
        return new ValidationResult(product, ruleEngine.evaluate(product));
    }
}
