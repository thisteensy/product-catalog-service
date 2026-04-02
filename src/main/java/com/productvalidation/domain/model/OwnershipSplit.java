package com.productvalidation.domain.model;

public class OwnershipSplit {

    private final String rightsHolder;
    private final double percentage;

    public OwnershipSplit(String rightsHolder, double percentage) {
        this.rightsHolder = rightsHolder;
        this.percentage = percentage;
    }

    public String getRightsHolder() { return rightsHolder; }
    public double getPercentage() { return percentage; }
}