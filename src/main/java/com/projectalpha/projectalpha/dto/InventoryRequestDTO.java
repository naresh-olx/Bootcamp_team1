package com.projectalpha.projectalpha.dto;

import lombok.Data;

import java.util.UUID;


@Data
public class InventoryRequestDTO {
    private String type;
    private String primaryStatus;
    private String primaryLocation;
    private int vin;
    private String make;
    private String model;
    private String year;
    private String trim;
    private String costPrice;
    private String sellingPrice;
    private UUID createdBy;
}
