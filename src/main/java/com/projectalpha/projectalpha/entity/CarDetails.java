package com.projectalpha.projectalpha.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "inventories")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarDetails {
    @Id
    private UUID carId;
    private String type;
    private String PrimaryStatus;
    private String PrimaryLocation;
    private int VIN;
    private String make;
    private String model;
    private String year;
    private String trim;
    private String costPrice;
    private String sellingPrice;
}

