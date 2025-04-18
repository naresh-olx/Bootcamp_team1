package com.projectalpha.controller;

import com.projectalpha.dto.ErrorResponse;
import com.projectalpha.dto.InventoryRequestDTO;
import com.projectalpha.dto.InventoryResponseDTO;
import com.projectalpha.entity.InventoryEntity;
import com.projectalpha.enums.InventoryStatus;
import com.projectalpha.service.InventoryServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
        InventoryResponseDTO mockInventory = InventoryResponseDTO.builder()
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

        ResponseEntity<?> response = inventoryController.deleteInventory(sku);
        assertEquals(OK, response.getStatusCode());
        verify(inventoryServices, times(1)).deleteInventoryItem(sku);
    }

    @Test
    void testDelete_WhenSkuDoesNotExist_Returns404() {
        String sku = "SKU_NOT_EXIST";
        String errorMessage = "Item not found";
        ResponseStatusException exception = new ResponseStatusException(NOT_FOUND, errorMessage);

        when(inventoryServices.deleteInventoryItem(sku)).thenThrow(exception);
        ResponseEntity<?> response = inventoryController.deleteInventory(sku);

        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(inventoryServices, times(1)).deleteInventoryItem(sku);
    }

    @Test
    void testDelete_WhenUnexpectedException_Returns500() {
        String sku = "SKU_EXCEPTION";
        when(inventoryServices.deleteInventoryItem(sku)).thenThrow(new RuntimeException("Internal Server Error"));
        ResponseEntity<?> response = inventoryController.deleteInventory(sku);

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

    @Test
    void updateInventory_Success() {
        String sku = "SKU123";
        InventoryResponseDTO responseDTO = InventoryResponseDTO.builder().sku(sku).make("toyota").vin(12367L).build();
        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setMake("Maruti");
        dto.setModel("first");

        when(inventoryServices.updateInventoryItem(sku,dto)).thenReturn(responseDTO);

        ResponseEntity<?> result = inventoryController.update(sku,dto);
        assertEquals(OK, result.getStatusCode());
        assertTrue(result.getBody() instanceof InventoryResponseDTO);
        verify(inventoryServices, times(1)).updateInventoryItem(sku,dto);
    }

    @Test
    void updateInventory_NotFound() {
        String sku = "SKU4563";
        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setMake("Tata");
        dto.setModel("second");

        when(inventoryServices.updateInventoryItem(sku,dto)).thenThrow(new ResponseStatusException(NOT_FOUND, "Item not found"));
        ResponseEntity<?> result = inventoryController.update(sku,dto);
        assertEquals(NOT_FOUND, result.getStatusCode());
        verify(inventoryServices, times(1)).updateInventoryItem(sku,dto);
    }

    @Test
    void updateInventory_InternalServerError() {
        String sku = "SKU0983";
        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setMake("Mahindra");
        dto.setModel("Third");
        when(inventoryServices.updateInventoryItem(sku,dto)).thenThrow(new RuntimeException("Internal Server Error"));
        ResponseEntity<?> result = inventoryController.update(sku,dto);

        assertEquals(INTERNAL_SERVER_ERROR, result.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("Internal Server Error",errorResponse.getErrorMessage());
        verify(inventoryServices, times(1)).updateInventoryItem(sku,dto);
    }

    @Test
    void updateStatus_Success() {
        InventoryStatus status = InventoryStatus.SOLD;
        String sku = "SKU123";
        InventoryResponseDTO item = InventoryResponseDTO.builder().sku(sku).make("Toyota").build();
        when(inventoryServices.updateInventoryStatus(sku,status)).thenReturn(item);
        ResponseEntity<?> result = inventoryController.updateStatus(sku,status);

        assertEquals(OK, result.getStatusCode());
        verify(inventoryServices, times(1)).updateInventoryStatus(sku,status);
    }

    @Test
    void updateStatus_NotFound() {
        String sku = "SKU404";
        InventoryStatus status = InventoryStatus.SOLD;
        when(inventoryServices.updateInventoryStatus(sku,status)).thenThrow(new ResponseStatusException(NOT_FOUND, "Item not found"));
        ResponseEntity<?> result = inventoryController.updateStatus(sku,status);

        assertEquals(NOT_FOUND, result.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("Item not found",errorResponse.getErrorMessage());
        verify(inventoryServices, times(1)).updateInventoryStatus(sku,status);
    }

    @Test
    void updateStatus_InternalServerError() {
        String sku = "SKU500";
        InventoryStatus status = InventoryStatus.SOLD;
        when(inventoryServices.updateInventoryStatus(sku,status)).thenThrow(new RuntimeException("Internal Server Error"));
        ResponseEntity<?> result = inventoryController.updateStatus(sku,status);

        assertEquals(INTERNAL_SERVER_ERROR, result.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("Internal Server Error",errorResponse.getErrorMessage());
        verify(inventoryServices, times(1)).updateInventoryStatus(sku,status);
    }


    private InventoryRequestDTO getInventoryRequestDTO() {
        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setMake("Mahindra");
        dto.setModel("Third");
        return dto;
    }

    private InventoryResponseDTO getInventoryResponseDTO() {
        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setMake("Thar");
        dto.setModel("Fourth");
        return dto;
    }

    @Test
    void createInventory_Success() {
        InventoryRequestDTO requestDTO = getInventoryRequestDTO();
        InventoryResponseDTO responseDTO = new InventoryResponseDTO();

        when(inventoryServices.saveInventory(requestDTO)).thenReturn(responseDTO);
        ResponseEntity<?> result = inventoryController.createInventory(requestDTO);

        assertEquals(CREATED, result.getStatusCode());
        assertTrue(result.getBody() instanceof InventoryResponseDTO);
        assertEquals(responseDTO,(InventoryResponseDTO) result.getBody());
        verify(inventoryServices, times(1)).saveInventory(requestDTO);
    }

    @Test
    void createInventory_ThrowsIllegalArgumentException() {
        InventoryRequestDTO requestDTO = getInventoryRequestDTO();

        when(inventoryServices.saveInventory(requestDTO)).thenThrow(new IllegalArgumentException("Invalid input"));
        ResponseEntity<?> result = inventoryController.createInventory(requestDTO);

        assertEquals(BAD_REQUEST, result.getStatusCode());
        assertTrue(result.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("Invalid input",errorResponse.getErrorMessage());
        verify(inventoryServices, times(1)).saveInventory(requestDTO);
    }

    @Test
    void createInventory_ThrowsResponseStatusException() {
        InventoryRequestDTO requestDTO = getInventoryRequestDTO();

        when(inventoryServices.saveInventory(requestDTO)).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Item already exists"));

        ResponseEntity<?> result = inventoryController.createInventory(requestDTO);

        assertEquals(CONFLICT, result.getStatusCode());
        assertTrue(result.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("Item already exists",errorResponse.getErrorMessage());
        verify(inventoryServices, times(1)).saveInventory(requestDTO);
    }

    @Test
    void createInventory_InternalServerError() {
        InventoryRequestDTO requestDTO = getInventoryRequestDTO();

        when(inventoryServices.saveInventory(requestDTO)).thenThrow(new RuntimeException("Internal Server Error"));
        ResponseEntity<?> result = inventoryController.createInventory(requestDTO);

        assertEquals(INTERNAL_SERVER_ERROR, result.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("Internal Server Error",errorResponse.getErrorMessage());
        verify(inventoryServices, times(1)).saveInventory(requestDTO);
    }
}


