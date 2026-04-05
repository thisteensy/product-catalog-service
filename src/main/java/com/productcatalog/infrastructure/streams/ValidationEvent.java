package com.productcatalog.infrastructure.streams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationEvent {
    public enum Type { PRODUCT, TRACK }

    private Type type;
    private String productId;
    private String trackId;
    private String status;
}