package com.projectalpha.projectalpha.service;

import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServicesTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServices inventoryServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetInventoryBySku_WhenSkuExists_ReturnsEntity() {
        String sku = "sku-123";

        InventoryEntity expected = InventoryEntity.builder().sku(sku).make("toyota").model("Good").build();

        when(inventoryRepository.findBySku(sku)).thenReturn(Optional.of(expected));

        InventoryEntity actual = inventoryServices.getInventoryBySku(sku);

        assertNotNull(actual);
        assertEquals(expected.getSku(), actual.getSku());
        assertEquals(expected.getModel(), actual.getModel());
        verify(inventoryRepository,times(1)).findBySku(sku);
    }

    @Test
    void testGetInventoryBySku_WhenSkuDoesNotExist_ThrowsException() {
        String sku = "sku-123";
        when(inventoryRepository.findBySku(sku)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> inventoryServices.getInventoryBySku(sku));

        assertEquals(404, exception.getStatusCode().value());
        verify(inventoryRepository,times(1)).findBySku(sku);
    }
}