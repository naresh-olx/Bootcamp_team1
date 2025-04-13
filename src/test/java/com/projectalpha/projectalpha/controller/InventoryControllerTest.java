package com.projectalpha.projectalpha.controller;

import com.projectalpha.projectalpha.dto.ErrorResponse;
import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.service.InventoryServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class InventoryControllerTest {

    @InjectMocks
    private InventoryController inventoryController;

    @Mock
    private InventoryServices inventoryServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBySku_Success() {
        String sku = "SKU123";
        InventoryEntity mockInventory = InventoryEntity.builder()
                .sku(sku)
                .vin(123456L)
                .make("Toyota")
                .build();

        when(inventoryServices.getInventoryBySku(sku)).thenReturn(mockInventory);
        ResponseEntity<?> response = inventoryController.getBySku(sku);
        assertEquals(OK, response.getStatusCode());
        assertEquals(mockInventory, response.getBody());
        verify(inventoryServices, times(1)).getInventoryBySku(sku);
    }

    @Test
    void testGetBySku_ItemNotFound() {
        String sku = "SKU_NOT_EXIST";

        ResponseStatusException exception = new ResponseStatusException(NOT_FOUND, "Item not found");
        when(inventoryServices.getInventoryBySku(sku)).thenThrow(exception);

        ResponseEntity<?> response = inventoryController.getBySku(sku);

        assertEquals(NOT_FOUND, response.getStatusCode());
        ErrorResponse error = (ErrorResponse) response.getBody();
        assertNotNull(error);
        assertEquals(NOT_FOUND, error.getErrorCode());
        assertEquals("Item not found", error.getErrorMessage());
        verify(inventoryServices, times(1)).getInventoryBySku(sku);
    }

    @Test
    void testGetBySku_UnexpectedException() {
        String sku = "SKU_EXCEPTION";
        when(inventoryServices.getInventoryBySku(sku)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = inventoryController.getBySku(sku);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to fetch Item: Internal Server Error",response.getBody().toString());
        verify(inventoryServices, times(1)).getInventoryBySku(sku);
    }

    @Test
    void testDelete_WhenSkuExists_ReturnsDeleteItem() {
        String sku = "SKU123";
        InventoryEntity deletedItem = InventoryEntity.builder().sku(sku).make("toyota").vin(12345L).build();
        when(inventoryServices.deleteInventoryItem(sku,null)).thenReturn(deletedItem);

        ResponseEntity<?> response = inventoryController.delete(sku);
        assertEquals(OK, response.getStatusCode());
        verify(inventoryServices, times(1)).deleteInventoryItem(sku,null);
    }

    @Test
    void testDelete_WhenSkuDoesNotExist_Returns404() {
        String sku = "SKU_NOT_EXIST";
        String errorMessage = "Item not found";
        ResponseStatusException exception = new ResponseStatusException(NOT_FOUND, errorMessage);

        when(inventoryServices.deleteInventoryItem(sku,null)).thenThrow(exception);
        ResponseEntity<?> response = inventoryController.delete(sku);

        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(inventoryServices, times(1)).deleteInventoryItem(sku,null);
    }
}


