package com.productcatalog.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStatusHistoryJpaRepository extends JpaRepository<ProductStatusHistoryEntity, String> {
}