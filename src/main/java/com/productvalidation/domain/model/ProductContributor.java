package com.productvalidation.domain.model;

public class ProductContributor {

    private final String name;
    private final ContributorRole role;

    public ProductContributor(String name, ContributorRole role) {
        this.name = name;
        this.role = role;
    }

    public String getName() { return name; }
    public ContributorRole getRole() { return role; }
}