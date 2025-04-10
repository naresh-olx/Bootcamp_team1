package com.projectalpha.projectalpha.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryEntity {
    @Id
    private String sku;

    private String type;

    private String status;

    private String primaryLocation;

//    private CarAttributes attributes;

//    private Pricing pricing;

//    private Metadata metadata;

//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class CarAttributes {
//        private String vin;
//        private String make;
//        private String model;
//        private String trim;
//        private Integer year;
//    }

//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class Pricing {
//        private BigDecimal cost;
//        private BigDecimal sellingPrice;
//    }
//
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class Metadata {
//        @CreatedDate
//        private Instant createdAt;
//
//        @LastModifiedDate
//        private Instant updatedAt;
//
//        private String createdBy;
//        private String updatedBy;
//    }
}
