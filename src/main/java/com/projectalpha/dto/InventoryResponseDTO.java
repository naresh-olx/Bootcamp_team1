package com.projectalpha.dto;

import com.projectalpha.enums.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponseDTO {
    private String sku;
    private String type;
    private InventoryStatus primaryStatus;
    private String primaryLocation;
    private Long vin;
    private String make;
    private String model;
    private String year;
    private String trim;
    private Double costPrice;
    private Double sellingPrice;

    private String  createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
