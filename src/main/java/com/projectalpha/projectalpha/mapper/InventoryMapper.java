package com.projectalpha.projectalpha.mapper;

import com.projectalpha.projectalpha.dto.InventoryRequestDTO;
import com.projectalpha.projectalpha.dto.InventoryResponseDTO;
import com.projectalpha.projectalpha.entity.InventoryEntity;

public class InventoryMapper {
    public static InventoryEntity toEntity(InventoryRequestDTO dto) {
        return InventoryEntity.builder()
                .type(dto.getType())
                .primaryStatus(dto.getPrimaryStatus())
                .primaryLocation(dto.getPrimaryLocation())
                .vin(dto.getVin())
                .make(dto.getMake())
                .model(dto.getModel())
                .trim(dto.getTrim())
                .year(dto.getYear())
                .costPrice(dto.getCostPrice())
                .sellingPrice(dto.getSellingPrice())
                .build();
    }

    public static InventoryResponseDTO toResponseDTO(InventoryEntity entity) {
        return InventoryResponseDTO.builder()
                .sku(entity.getSku())
                .type(entity.getType())
                .primaryStatus(entity.getPrimaryStatus())
                .primaryLocation(entity.getPrimaryLocation())
                .vin(entity.getVin())
                .make(entity.getMake())
                .model(entity.getModel())
                .trim(entity.getTrim())
                .year(entity.getYear())
                .costPrice(entity.getCostPrice())
                .sellingPrice(entity.getSellingPrice())
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedBy(entity.getUpdatedBy())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
