package com.productcatalog.infrastructure.streams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductValidationState {

    private String productStatus;
    private Map<String, String> trackStatuses = new HashMap<>();

    @JsonIgnore
    public boolean allTracksValidated() {
        return !trackStatuses.isEmpty() && trackStatuses.values().stream()
                .allMatch(s -> s.equals("VALIDATED"));
    }

    @JsonIgnore
    public boolean isAwaitingTrackValidation() {
        return "AWAITING_TRACK_VALIDATION".equals(productStatus);
    }
}