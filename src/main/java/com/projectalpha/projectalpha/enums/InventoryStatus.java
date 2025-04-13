package com.projectalpha.projectalpha.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid inventory status value, this can be only CREATED or PROCURED or SOLD");
    }
}
