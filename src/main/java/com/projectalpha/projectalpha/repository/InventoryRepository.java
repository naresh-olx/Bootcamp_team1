package com.projectalpha.projectalpha.repository;

import com.projectalpha.projectalpha.entity.InventoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface InventoryRepository extends MongoRepository<InventoryEntity, String> {
    Optional<InventoryEntity> findBySku(String sku);
}
