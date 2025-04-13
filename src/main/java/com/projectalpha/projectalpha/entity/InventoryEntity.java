package com.projectalpha.projectalpha.entity;

import com.projectalpha.projectalpha.enums.InventoryStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryEntity {
    @Id
    private String sku = UUID.randomUUID().toString().split("-")[0];

    private String type;
    private InventoryStatus primaryStatus;
    private String primaryLocation;

    @Indexed(unique = true)
    private Long vin;

    private String make;
    private String model;
    private String year;
    private String trim;
    private Double costPrice;
    private Double sellingPrice;

    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;
}
