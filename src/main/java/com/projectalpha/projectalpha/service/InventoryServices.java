package com.projectalpha.projectalpha.service;

import com.projectalpha.projectalpha.customException.DuplicateSkuException;
import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.repository.InventoryRepository;
import com.projectalpha.projectalpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class InventoryServices {

    @Autowired
    private InventoryRepository inventoryRepository;
    UserRepository userRepository;

    public InventoryEntity saveInventoryItem(InventoryEntity inventoryEntity) {
        String user = inventoryEntity.getCreatedBy();
        boolean userExists = userRepository.existsById(user);
        if(!userExists){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist with ID: " + user);
        }

        if (inventoryRepository.existsById(inventoryEntity.getSku())) {
            throw new DuplicateSkuException("Inventory with SKU '" + inventoryEntity.getSku() + "' already exists.");
        }

        if (inventoryRepository.existsById(String.valueOf(inventoryEntity.getSku()))) {
            throw new DuplicateSkuException("Inventory with SKU '" + inventoryEntity.getSku() + "' already exists.");
        }
        InventoryEntity Saveditem = inventoryRepository.insert(inventoryEntity);
        return Saveditem;
    }

    public Page<InventoryEntity> getAllInventories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return inventoryRepository.findAll(pageable);
    }

    public InventoryEntity getInventoryBySku(String sku) {
        return inventoryRepository.findBySku(sku).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public InventoryEntity updateInventory(String sku, InventoryEntity inventoryEntity) {

    }
}
