package com.productcatalog.infrastructure.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class ValidationEventSerde implements Serde<ValidationEvent> {

    private final ObjectMapper objectMapper;

    public ValidationEventSerde(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Serializer<ValidationEvent> serializer() {
        return (topic, data) -> {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize ValidationEvent", e);
            }
        };
    }

    @Override
    public Deserializer<ValidationEvent> deserializer() {
        return (topic, data) -> {
            try {
                if (data == null) return null;
                return objectMapper.readValue(data, ValidationEvent.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to deserialize ValidationEvent", e);
            }
        };
    }
}