package com.projectalpha.projectalpha.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document(collection = "inventories")
@Data
@NoArgsConstructor
public class InventoryEntity {
    @Id
    private String sku = UUID.randomUUID().toString();

    private String type;
    private String PrimaryStatus;
    private String PrimaryLocation;
    private int vin;
    private String make;
    private String model;
    private String year;
    private String trim;
    private String costPrice;
    private String sellingPrice;

    private UUID createdBy;
    private Date createdAt;
    private UUID updatedBy;
    private Date updatedAt;
}
