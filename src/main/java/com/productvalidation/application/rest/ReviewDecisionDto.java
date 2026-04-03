package com.productvalidation.application.rest;

import com.productvalidation.domain.model.ProductStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDecisionDto {
    private ProductStatus status;
    private String notes;
}