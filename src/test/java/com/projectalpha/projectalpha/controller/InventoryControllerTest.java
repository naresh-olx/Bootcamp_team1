package com.projectalpha.projectalpha.controller;

import com.projectalpha.projectalpha.dto.ErrorResponse;
import com.projectalpha.projectalpha.dto.InventoryResponseDTO;
import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.service.InventoryServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        InventoryResponseDTO deletedItem = InventoryResponseDTO.builder().sku(sku).make("toyota").vin(12345L).build();
        when(inventoryServices.deleteInventoryItem(sku)).thenReturn(deletedItem);

        ResponseEntity<?> response = inventoryController.delete(sku);
        assertEquals(OK, response.getStatusCode());
        verify(inventoryServices, times(1)).deleteInventoryItem(sku);
    }

    @Test
    void testDelete_WhenSkuDoesNotExist_Returns404() {
        String sku = "SKU_NOT_EXIST";
        String errorMessage = "Item not found";
        ResponseStatusException exception = new ResponseStatusException(NOT_FOUND, errorMessage);

        when(inventoryServices.deleteInventoryItem(sku)).thenThrow(exception);
        ResponseEntity<?> response = inventoryController.delete(sku);

        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(inventoryServices, times(1)).deleteInventoryItem(sku);
    }

    @Test
    void testDelete_WhenUnexpectedException_Returns500() {
        String sku = "SKU_EXCEPTION";
        when(inventoryServices.deleteInventoryItem(sku)).thenThrow(new RuntimeException("Internal Server Error"));
        ResponseEntity<?> response = inventoryController.delete(sku);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        verify(inventoryServices, times(1)).deleteInventoryItem(sku);
    }

    @Test
    void testGetAllInventory_Success() {
        int page = 0;
        int size = 2;

        InventoryEntity item1 = InventoryEntity.builder().sku("SKU001").make("Toyota").build();
        InventoryEntity item2 = InventoryEntity.builder().sku("SKU002").make("Honda").build();

        List<InventoryEntity> inventoryList = List.of(item1, item2);
        Page<InventoryEntity> mockPage = new PageImpl<>(inventoryList, PageRequest.of(page, size), inventoryList.size());

        when(inventoryServices.getAllInventories(page, size)).thenReturn(mockPage);

        ResponseEntity<?> response = inventoryController.getAllInventory(page, size);

        assertEquals(OK, response.getStatusCode());
        assertInstanceOf(Page.class, response.getBody());

        Page<?> returnedPage = (Page<?>) response.getBody();
        assertEquals(2, returnedPage.getContent().size());
        assertTrue(returnedPage.getContent().contains(item1));
        assertTrue(returnedPage.getContent().contains(item2));

        verify(inventoryServices, times(1)).getAllInventories(page, size);
    }

    @Test
    void testGetAllInventory_UnexpectedException() {
        int page = 0;
        int size = 5;

        when(inventoryServices.getAllInventories(page, size))
                .thenThrow(new RuntimeException("Database connection failed"));

        ResponseEntity<?> response = inventoryController.getAllInventory(page, size);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Failed to fetch inventories: Database connection failed"));

        verify(inventoryServices, times(1)).getAllInventories(page, size);
    }
}


