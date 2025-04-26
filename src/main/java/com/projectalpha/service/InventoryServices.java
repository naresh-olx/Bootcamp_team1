package com.projectalpha.service;

import com.projectalpha.dto.InventoryRequestDTO;
import com.projectalpha.dto.InventoryResponseDTO;
import com.projectalpha.entity.InventoryEntity;
import com.projectalpha.entity.UserEntity;
import com.projectalpha.enums.InventoryStatus;
import com.projectalpha.mapper.InventoryMapper;
import com.projectalpha.repository.InventoryRepository;
import com.projectalpha.repository.UserRepository;
import jakarta.validation.Valid;
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
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;

    public InventoryServices(InventoryRepository inventoryRepository, UserRepository userRepository) {
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
    }

    public InventoryResponseDTO saveInventory(@Valid InventoryRequestDTO saveDTO) {
        String userId = userIdGetterAndAuthenticator();
        if (saveDTO.getVin() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "VIN is required");
        }
        boolean vinExists = inventoryRepository.existsByVin(saveDTO.getVin());
        if (vinExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Inventory with this VIN already exists");
        }

        InventoryEntity entityToSave = InventoryMapper.toEntity(saveDTO);
        entityToSave.setCreatedBy(userId);
        entityToSave.setCreatedAt(LocalDateTime.now());
        entityToSave.setUpdatedBy(userId);
        entityToSave.setUpdatedAt(LocalDateTime.now());

        return InventoryMapper.toResponseDTO(inventoryRepository.insert(entityToSave));
    }

    public Page<InventoryEntity> getAllInventories(int page, int size) {
        String userId = userIdGetterAndAuthenticator();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return inventoryRepository.findAllByCreatedBy(userId, pageable);
    }

    public InventoryResponseDTO getInventoryBySku(String sku) {
        String userId = userIdGetterAndAuthenticator();
        InventoryEntity inventoryToGet = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found"));
        userIdAuthorizer(userId, inventoryToGet);
        return InventoryMapper.toResponseDTO(inventoryToGet);
    }

    public InventoryResponseDTO updateInventoryItem(String sku, InventoryRequestDTO updateDTO) {
        String userId = userIdGetterAndAuthenticator();
        InventoryEntity inventoryToUpdate = inventoryRepository.findById(sku)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Inventory not found with given SKU: " + sku));
        userIdAuthorizer(userId, inventoryToUpdate);
        if(updateDTO.getVin() != null) {
            inventoryToUpdate.setVin(updateDTO.getVin());
        }
        if (updateDTO.getType() != null){
            inventoryToUpdate.setType(updateDTO.getType());
        }
        if (updateDTO.getPrimaryStatus() != null){
            inventoryToUpdate.setPrimaryStatus(updateDTO.getPrimaryStatus());
        }
        if (updateDTO.getPrimaryLocation() != null){
            inventoryToUpdate.setPrimaryLocation(updateDTO.getPrimaryLocation());
        }
        if (updateDTO.getMake() != null){
            inventoryToUpdate.setMake(updateDTO.getMake());
        }
        if (updateDTO.getModel() != null){
            inventoryToUpdate.setModel(updateDTO.getModel());
        }
        if (updateDTO.getYear() != null){
            inventoryToUpdate.setYear(updateDTO.getYear());
        }
        if (updateDTO.getTrim() != null){
            inventoryToUpdate.setTrim(updateDTO.getTrim());
        }
        if(updateDTO.getCostPrice() != null){
            inventoryToUpdate.setCostPrice(updateDTO.getCostPrice());
        }
        if(updateDTO.getSellingPrice() != null){
            inventoryToUpdate.setSellingPrice(updateDTO.getSellingPrice());
        }
        inventoryToUpdate.setUpdatedBy(userId);
        inventoryToUpdate.setUpdatedAt(LocalDateTime.now());
        return InventoryMapper.toResponseDTO(inventoryRepository.save(inventoryToUpdate));
    }

    public InventoryResponseDTO updateInventoryStatus(String sku, InventoryStatus status) {
        String userId = userIdGetterAndAuthenticator();
        InventoryEntity inventory = inventoryRepository.findById(sku)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Inventory not found with given SKU: " + sku));
        userIdAuthorizer(userId, inventory);
        inventory.setPrimaryStatus(status);
        inventory.setUpdatedBy(userId);
        return InventoryMapper.toResponseDTO(inventoryRepository.save(inventory));
    }

    public InventoryResponseDTO deleteInventoryItem(String sku) {
        String userId = userIdGetterAndAuthenticator();
        InventoryEntity inventoryToDelete = inventoryRepository.findById(sku)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Inventory not found with given SKU: " + sku));
        userIdAuthorizer(userId, inventoryToDelete);
        inventoryRepository.deleteById(sku);
        return InventoryMapper.toResponseDTO(inventoryToDelete);
    }

    private String userIdGetterAndAuthenticator() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailId = authentication.getName();
        UserEntity userEntity = userRepository.findByEmailId(emailId);
        if(userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not exists with given EmailId: " + emailId);
        }
        return userEntity.getUserId();
    }

    private static void userIdAuthorizer(String userId, InventoryEntity inventory) {
        if (!inventory.getCreatedBy().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "User is not authorized for this inventory item");
        }
    }
}
