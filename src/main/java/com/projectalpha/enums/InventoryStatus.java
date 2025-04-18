package com.projectalpha.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public enum InventoryStatus {
    CREATED,
    PROCURED,
    SOLD;

    @JsonValue
    public String toValue() {
        return this.name();
    }

    @JsonCreator
    public static InventoryStatus fromValue(String value) throws InvalidFormatException {
        for (InventoryStatus status : InventoryStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw InvalidFormatException.from(
                null,
                "InventoryStatus",
                value, InventoryStatus.class
        );
    }
}
