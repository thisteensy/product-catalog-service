package com.productvalidation.infrastructure.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productvalidation.domain.model.*;
import com.productvalidation.domain.ports.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;
    private final ObjectMapper objectMapper;

    public ProductRepositoryImpl(ProductJpaRepository jpaRepository, ObjectMapper objectMapper) {
        this.jpaRepository = jpaRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void updateStatus(UUID id, ProductStatus status) {
        jpaRepository.findById(id.toString()).ifPresent(entity -> {
            entity.setStatus(status.name());
            jpaRepository.save(entity);
        });
    }

    @Override
    public List<Product> findByStatus(ProductStatus status) {
        return jpaRepository.findByStatus(status.name()).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id.toString())
                .map(this::toDomain);
    }

    private Product toDomain(ProductEntity entity) {
        try {
            return new Product(
                    UUID.fromString(entity.getId()),
                    entity.getUpc(),
                    entity.getIsrc(),
                    entity.getTitle(),
                    objectMapper.readValue(entity.getContributors(),
                            new TypeReference<List<ProductContributor>>() {}),
                    entity.getReleaseDate(),
                    entity.getGenre(),
                    entity.isExplicit(),
                    entity.getLanguage(),
                    objectMapper.readValue(entity.getOwnershipSplits(),
                            new TypeReference<List<OwnershipSplit>>() {}),
                    entity.getAudioFileUri(),
                    entity.getArtworkUri(),
                    objectMapper.readValue(entity.getDspTargets(),
                            new TypeReference<List<String>>() {}),
                    ProductStatus.valueOf(entity.getStatus())
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize product entity: " + entity.getId(), e);
        }
    }
}