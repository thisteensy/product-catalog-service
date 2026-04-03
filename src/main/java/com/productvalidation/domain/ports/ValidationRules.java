package com.productvalidation.domain.ports;

import com.productvalidation.domain.model.Product;
import com.productvalidation.domain.model.RuleResult;

import java.util.List;

public interface ValidationRules {
    List<RuleResult> evaluate(Product product);
}
