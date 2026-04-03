package com.productvalidation;

import com.productvalidation.domain.ports.RuleEngine;
import com.productvalidation.domain.ports.ValidationService;
import com.productvalidation.domain.service.ValidationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public ValidationService validationService(RuleEngine ruleEngine) {
        return new ValidationServiceImpl(ruleEngine);
    }
}