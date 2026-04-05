package com.productcatalog.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductContributor {

    private final String name;
    private final ContributorRole role;

    @JsonCreator
    public ProductContributor(
            @JsonProperty("name") String name,
            @JsonProperty("role") ContributorRole role) {
        this.name = name;
        this.role = role;
    }
}