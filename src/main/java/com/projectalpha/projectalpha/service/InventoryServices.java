package com.projectalpha.projectalpha.service;

import com.projectalpha.projectalpha.customException.DuplicateSkuException;
import com.projectalpha.projectalpha.dto.UpdateDTO;
import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.repository.InventoryRepository;
import com.projectalpha.projectalpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InventoryServices {

    @Autowired
    private InventoryRepository inventoryRepository;
    UserRepository userRepository;

    public InventoryEntity saveInventory(InventoryEntity inventoryEntity) {

        String user = inventoryEntity.getCreatedBy();

        boolean userExists = userRepository.existsById(user);

        if(!userExists){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist with ID: " + user);
        }

        if (inventoryRepository.existsById(inventoryEntity.getSku())) {
            throw new DuplicateSkuException("Inventory with SKU '" + inventoryEntity.getSku() + "' already exists.");
        }

        return inventoryRepository.insert(inventoryEntity);
    }

    public Page<InventoryEntity> getAllInventories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return inventoryRepository.findAll(pageable);
    }

    public InventoryEntity getInventoryBySku(String sku) {
        return inventoryRepository.findBySku(sku).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public InventoryEntity updateInventoryItem(String sku, UpdateDTO updateDTO) {
        String userId = updateDTO.getUserId();
        userIdAndSkuValidator(sku, userId);
        InventoryEntity updatedInventory = inventoryRepository.findById(sku).get();
        if(updateDTO.getVin() != null) {
            updatedInventory.setVin(updateDTO.getVin());
        }
        if (updateDTO.getType() != null){
            updatedInventory.setType(updateDTO.getType());
        }
        if (updateDTO.getPrimaryStatus() != null){
            updatedInventory.setPrimaryStatus(updateDTO.getPrimaryStatus());
        }
        if (updateDTO.getPrimaryLocation() != null){
            updatedInventory.setPrimaryLocation(updateDTO.getPrimaryLocation());
        }
        if (updateDTO.getMake() != null){
            updatedInventory.setMake(updateDTO.getMake());
        }
        if (updateDTO.getModel() != null){
            updatedInventory.setModel(updateDTO.getModel());
        }
        if (updateDTO.getYear() != null){
            updatedInventory.setYear(updateDTO.getYear());
        }
        if (updateDTO.getTrim() != null){
            updatedInventory.setTrim(updateDTO.getTrim());
        }
        if(updateDTO.getCostPrice() != null){
            updatedInventory.setCostPrice(updateDTO.getCostPrice());
        }
        if(updateDTO.getSellingPrice() != null){
            updatedInventory.setSellingPrice(updateDTO.getSellingPrice());
        }
        updatedInventory.setUpdatedBy(updateDTO.getUserId());
        return inventoryRepository.save(updatedInventory);
    }

    public InventoryEntity deleteInventoryItem(String sku, String userId) {
        userIdAndSkuValidator(sku, userId);
        InventoryEntity deletedInventory = inventoryRepository.findById(sku).get();
        inventoryRepository.deleteById(sku);
        return deletedInventory;
    }

    private void userIdAndSkuValidator(String sku, String userId) {
        boolean userExists = userRepository.existsById(userId);
        if (!userExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist with given Id: " + userId);
        }
        boolean skuExists = inventoryRepository.existsById(sku);
        if (!skuExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sku doesn't exist with given Id: " + sku);
        }
    }

    public InventoryEntity updateInventoryStatus(String sku, String status, String userId) {
        userIdAndSkuValidator(sku, userId);

        InventoryEntity inventory = inventoryRepository.findById(sku)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sku doesn't exist with given Id: " + sku));

        inventory.setPrimaryStatus(status);
        inventory.setUpdatedBy(userId);
        return inventoryRepository.save(inventory);
    }

}
