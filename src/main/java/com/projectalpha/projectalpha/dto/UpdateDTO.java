package com.projectalpha.projectalpha.dto;

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
public class UpdateDTO {

    @NotBlank(message = "UserId is required")
    private String userId;

    private String type;
    private String primaryStatus;
    private String primaryLocation;

    @NotNull(message = "VIN is required")
    @Positive(message = "VIN must be positive")
    private Long vin;

    private String make;
    private String model;
    private String year;
    private String trim;

    @Positive(message = "Cost price must be positive")
    private Double costPrice;

    @Positive(message = "Selling price must be positive")
    private Double sellingPrice;
}
