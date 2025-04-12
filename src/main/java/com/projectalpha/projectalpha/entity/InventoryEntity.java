package com.projectalpha.projectalpha.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
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
    private String primaryStatus;
    private String primaryLocation;
    private Long vin;
    private String make;
    private String model;
    private String year;
    private String trim;
    private double costPrice;
    private double sellingPrice;

    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
