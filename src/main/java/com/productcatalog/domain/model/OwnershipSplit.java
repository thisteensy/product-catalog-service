package com.productcatalog.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OwnershipSplit {

    private final String rightsHolder;
    private final double percentage;

    @JsonCreator
    public OwnershipSplit(
            @JsonProperty("rightsHolder") String rightsHolder,
            @JsonProperty("percentage") double percentage) {
        this.rightsHolder = rightsHolder;
        this.percentage = percentage;
    }
}