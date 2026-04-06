package com.productcatalog.application.kafka.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productcatalog.application.kafka.dtos.ProductEventDto;
import com.productcatalog.domain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Component
public class ProductEventMapper {

    private final ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(ProductEventMapper.class);

    public ProductEventMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ProductEventDto toDto(String message) {
        try {
            return objectMapper.readValue(message, ProductEventDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize product event", e);
        }
    }

    public Product fromProductRowToProduct(ProductEventDto.ProductRow row) {
        try {
            return Product.builder()
                    .id(UUID.fromString(row.getId()))
                    .upc(row.getUpc() == null ? null : row.getUpc().strip().replace("-", "").replace(" ", ""))
                    .title(row.getTitle() == null ? null : row.getTitle().strip())
                    .tracks(null)
                    .releaseDate(LocalDate.ofEpochDay(row.getReleaseDate()))
                    .genre(row.getGenre() == null ? null : row.getGenre().strip())
                    .language(row.getLanguage() == null ? null : row.getLanguage().strip().toLowerCase(Locale.ROOT))
                    .ownershipSplits(row.getOwnershipSplits() == null ? null :
                            objectMapper.readValue(row.getOwnershipSplits(),
                                            new TypeReference<List<OwnershipSplit>>() {}).stream()
                                    .map(o -> new OwnershipSplit(
                                            o.getRightsHolder() == null ? null : o.getRightsHolder().strip(),
                                            o.getPercentage()))
                                    .toList())
                    .artworkUri(row.getArtworkUri() == null ? null : row.getArtworkUri().strip())
                    .dspTargets(row.getDspTargets() == null ? null :
                            objectMapper.readValue(row.getDspTargets(),
                                            new TypeReference<List<String>>() {}).stream()
                                    .map(t -> t == null ? null : t.strip().toLowerCase(Locale.ROOT))
                                    .toList())
                    .status(ProductStatus.valueOf(row.getStatus()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to map product event: " + row.getId(), e);
        }
    }

    public List<String> parseDspTargets(String dspTargetsJson) {
        if (dspTargetsJson == null) return List.of();
        try {
            return objectMapper.readValue(dspTargetsJson, new TypeReference<List<String>>() {}).stream()
                    .map(t -> t == null ? null : t.strip().toLowerCase(Locale.ROOT))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse DSP targets", e);
        }
    }
}