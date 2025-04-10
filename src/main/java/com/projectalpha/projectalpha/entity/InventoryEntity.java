package com.projectalpha.projectalpha.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document(collection = "inventories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryEntity {
    @Id
    private UUID sku;
    private UUID carId;
    private UUID createdBy;
    private Date createdAt;
    private UUID updatedBy;
    private Date updatedAt;
}
