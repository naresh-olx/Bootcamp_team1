package com.projectalpha.projectalpha.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum InventoryStatus {
    CREATED,
    PROCURED,
    SOLD;

    @JsonValue
    public String toValue() {
        return this.name();
    }

    @JsonCreator
    public static InventoryStatus fromValue(String value) {
        for (InventoryStatus status : InventoryStatus.values()) {
            if (status.name().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }
}
