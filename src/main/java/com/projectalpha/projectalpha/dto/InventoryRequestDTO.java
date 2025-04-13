package com.projectalpha.projectalpha.dto;

import com.projectalpha.projectalpha.enums.InventoryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequestDTO {

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Primary status is required")
    private InventoryStatus primaryStatus;

    @NotBlank(message = "Primary location is required")
    private String primaryLocation;

    @NotNull(message = "VIN is required")
    @Positive(message = "VIN must be positive")
    private Long vin;

    @NotBlank(message = "Make is required")
    private String make;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Year is required")
    private String year;

    @NotBlank(message = "Trim is required")
    private String trim;

    @NotNull(message = "Cost price is required")
    @Positive(message = "Cost price must be positive")
    private double costPrice;

    @NotNull(message = "Selling price is required")
    @Positive(message = "Selling price must be positive")
    private double sellingPrice;
}
