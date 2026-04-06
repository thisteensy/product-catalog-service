package com.productcatalog.infrastructure.streams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationEvent {
    public enum Type { PRODUCT, TRACK }

    private Type type;
    private String productId;
    private String trackId;
    private String status;
    private List<String> dspTargets;
}