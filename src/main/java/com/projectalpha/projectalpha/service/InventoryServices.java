package com.projectalpha.projectalpha.service;

import com.projectalpha.projectalpha.customException.DuplicateSkuException;
import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryServices {

    @Autowired
    InventoryRepository inventoryRepository;

    public InventoryEntity saveInventoryItem(InventoryEntity inventoryEntity) {
        if (inventoryRepository.existsById(inventoryEntity.getSku())) {
            throw new DuplicateSkuException("Inventory with SKU '" + inventoryEntity.getSku() + "' already exists.");
        }
        InventoryEntity item = inventoryRepository.insert(inventoryEntity);
        return item;
    }
}
