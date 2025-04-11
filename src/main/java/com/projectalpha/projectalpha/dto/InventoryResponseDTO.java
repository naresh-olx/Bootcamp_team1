package com.projectalpha.projectalpha.dto;

import lombok.Data;

import java.util.Date;

@Data
public class InventoryResponseDTO {
    private String sku;
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

    private UserRequest createdBy;
    private Date createdAt;
    private UserRequest updatedBy;
    private Date updatedAt;
}
