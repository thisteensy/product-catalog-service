package com.productcatalog.infrastructure.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class ProductValidationStateSerde implements Serde<ProductValidationState> {

    private final ObjectMapper objectMapper;

    public ProductValidationStateSerde(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Serializer<ProductValidationState> serializer() {
        return (topic, data) -> {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize ProductValidationState", e);
            }
        };
    }

    @Override
    public Deserializer<ProductValidationState> deserializer() {
        return (topic, data) -> {
            try {
                if (data == null) { return null; };
                return objectMapper.readValue(data, ProductValidationState.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to deserialize ProductValidationState", e);
            }
        };
    }
}