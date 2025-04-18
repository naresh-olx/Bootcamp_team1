package com.projectalpha.repository;

import com.projectalpha.entity.InventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

@Component
public interface InventoryRepository extends
        MongoRepository<InventoryEntity, String>,
        PagingAndSortingRepository<InventoryEntity, String> {
    Optional<InventoryEntity> findBySku(String sku);
    Page<InventoryEntity> findAllByCreatedBy(String userId, Pageable pageable);
    boolean existsByVin(Long vin);
}
