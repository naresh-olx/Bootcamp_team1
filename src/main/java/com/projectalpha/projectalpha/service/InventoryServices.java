package com.projectalpha.projectalpha.service;

import com.projectalpha.projectalpha.dto.InventoryRequestDTO;
import com.projectalpha.projectalpha.dto.InventoryResponseDTO;
import com.projectalpha.projectalpha.dto.UpdateDTO;
import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.entity.UserEntity;
import com.projectalpha.projectalpha.enums.InventoryStatus;
import com.projectalpha.projectalpha.mapper.InventoryMapper;
import com.projectalpha.projectalpha.repository.InventoryRepository;
import com.projectalpha.projectalpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class InventoryServices {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    UserRepository userRepository;

    public InventoryResponseDTO saveInventory(InventoryRequestDTO inventoryEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailId = authentication.getName();
        UserEntity userEntity = userRepository.findByEmailId(emailId);

        if (inventoryEntity.getVin() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "VIN is required");
        }

        if(userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user doesn't exist");
        }

        boolean vinExists = inventoryRepository.existsByVin(inventoryEntity.getVin());

        if (vinExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Inventory with this VIN already exists");
        }

        InventoryEntity entityToSave = InventoryMapper.toEntity(inventoryEntity);
        entityToSave.setCreatedBy(userEntity.getUserId());
        entityToSave.setCreatedAt(LocalDateTime.now());
        entityToSave.setUpdatedBy(userEntity.getUserId());
        entityToSave.setUpdatedAt(LocalDateTime.now());

        return InventoryMapper.toResponseDTO(inventoryRepository.insert(entityToSave));
    }

    public Page<InventoryEntity> getAllInventories(int page, int size) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailId = authentication.getName();
        UserEntity userEntity = userRepository.findByEmailId(emailId);

        if(userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user doesn't exist");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return inventoryRepository.findAllByCreatedBy(userEntity.getUserId(), pageable);
    }

    public InventoryResponseDTO getInventoryBySku(String sku) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailId = authentication.getName();
        UserEntity userEntity = userRepository.findByEmailId(emailId);

        if(userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user doesn't exist");
        }

        InventoryEntity inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found"));

        if (!inventory.getCreatedBy().equals(userEntity.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this inventory item");
        }
        return InventoryMapper.toResponseDTO(inventory);
    }

    public InventoryResponseDTO updateInventoryItem(String sku, UpdateDTO updateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailId = authentication.getName();
        UserEntity userEntity = userRepository.findByEmailId(emailId);

        userIdAndSkuValidator(sku);
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
        updatedInventory.setUpdatedBy(userEntity.getUserId());
        updatedInventory.setUpdatedAt(LocalDateTime.now());
        return InventoryMapper.toResponseDTO(inventoryRepository.save(updatedInventory));
    }

    public InventoryResponseDTO deleteInventoryItem(String sku) {
        userIdAndSkuValidator(sku);
        InventoryEntity deletedInventory = inventoryRepository.findById(sku).get();
        inventoryRepository.deleteById(sku);
        return InventoryMapper.toResponseDTO(deletedInventory);
    }

    private void userIdAndSkuValidator(String sku) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailId = authentication.getName();
        UserEntity userEntity = userRepository.findByEmailId(emailId);

        if(userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist with given EmailId: " + emailId);
        }

        InventoryEntity inventory = inventoryRepository.findById(sku).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found"));

        if (!inventory.getCreatedBy().equals(userEntity.getUserId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user is unauthorised");
        }
    }

    public InventoryResponseDTO updateInventoryStatus(String sku, InventoryStatus status, String userId) {
        userIdAndSkuValidator(sku);

        InventoryEntity inventory = inventoryRepository.findById(sku)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sku doesn't exist with given Id: " + sku));

        inventory.setPrimaryStatus(status);
        inventory.setUpdatedBy(userId);
        return InventoryMapper.toResponseDTO(inventoryRepository.save(inventory));
    }

}
