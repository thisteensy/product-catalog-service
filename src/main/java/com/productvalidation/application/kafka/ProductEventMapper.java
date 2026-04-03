package com.productvalidation.application.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productvalidation.domain.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class ProductEventMapper {

    private final ObjectMapper objectMapper;

    public ProductEventMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Product toDomain(ProductEventDto.ProductRow row) {
        try {
            return new Product(
                    UUID.fromString(row.getId()),
                    row.getUpc(),
                    row.getIsrc(),
                    row.getTitle(),
                    objectMapper.readValue(row.getContributors(),
                            new TypeReference<List<ProductContributor>>() {}),
                    LocalDate.ofEpochDay(row.getReleaseDate()),
                    row.getGenre(),
                    row.getExplicit() != null && row.getExplicit() == 1,
                    row.getLanguage(),
                    objectMapper.readValue(row.getOwnershipSplits(),
                            new TypeReference<List<OwnershipSplit>>() {}),
                    row.getAudioFileUri(),
                    row.getArtworkUri(),
                    objectMapper.readValue(row.getDspTargets(),
                            new TypeReference<List<String>>() {}),
                    ProductStatus.valueOf(row.getStatus())
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to map product event: " + row.getId(), e);
        }
    }
}