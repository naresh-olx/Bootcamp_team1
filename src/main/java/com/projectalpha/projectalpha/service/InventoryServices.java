package com.projectalpha.projectalpha.service;

import com.projectalpha.projectalpha.customException.DuplicateSkuException;
import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InventoryServices {

    @Autowired
    InventoryRepository inventoryRepository;

    public InventoryEntity saveInventoryItem(InventoryEntity inventoryEntity, UUID userId) {
        if (inventoryRepository.existsById(String.valueOf(inventoryEntity.getSku()))) {
            throw new DuplicateSkuException("Inventory with SKU '" + inventoryEntity.getSku() + "' already exists.");
        }
        InventoryEntity Saveditem = inventoryRepository.insert(inventoryEntity);
        return Saveditem;
    }
}
