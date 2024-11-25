package com.gamesUP.gamesUP.controller.client;

import com.gamesUP.gamesUP.DTO.Purchase.PurchaseDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseLineDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseLineResponseDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseResponseDTO;
import com.gamesUP.gamesUP.service.Purchase.PurchaseLineService;
import com.gamesUP.gamesUP.service.Purchase.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClientPurchaseControllerTests {

    @InjectMocks
    private ClientPurchaseController clientPurchaseController;

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private PurchaseLineService purchaseLineService;

    private PurchaseResponseDTO purchaseResponseDTO;
    private PurchaseLineResponseDTO purchaseLineResponseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        purchaseResponseDTO = new PurchaseResponseDTO();
        purchaseResponseDTO.setId(1L);

        purchaseLineResponseDTO = new PurchaseLineResponseDTO();
        purchaseLineResponseDTO.setId(1L);
    }

    @Test
    public void testGetPurchaseById() {
        // Arrange
        when(purchaseService.getPurchaseById(1L)).thenReturn(purchaseResponseDTO);

        // Act
        PurchaseResponseDTO result = clientPurchaseController.getPurchaseById(1L);

        // Assert
        assertEquals(purchaseResponseDTO.getId(), result.getId());
    }

    @Test
    public void testAddPurchase() {
        // Arrange
        PurchaseDTO purchaseDTO = new PurchaseDTO();

        // Act
        clientPurchaseController.addPurchase(purchaseDTO);

        // Assert
        verify(purchaseService, times(1)).addPurchase(purchaseDTO);
    }

    @Test
    public void testUpdatePurchase() {
        // Arrange
        PurchaseDTO purchaseDTO = new PurchaseDTO();

        // Act
        clientPurchaseController.updatePurchase(1L, purchaseDTO);

        // Assert
        verify(purchaseService, times(1)).updatePurchase(1L, purchaseDTO);
    }

    @Test
    public void testGetPurchaseLineById() {
        // Arrange
        when(purchaseLineService.getPurchaseLineById(1L)).thenReturn(purchaseLineResponseDTO);

        // Act
        PurchaseLineResponseDTO result = clientPurchaseController.getPurchaseLineById(1L);

        // Assert
        assertEquals(purchaseLineResponseDTO.getId(), result.getId());
    }

    @Test
    public void testAddPurchaseLine() {
        // Arrange
        PurchaseLineDTO purchaseLineDTO = new PurchaseLineDTO();

        // Act
        clientPurchaseController.addPurchaseLine(purchaseLineDTO);

        // Assert
        verify(purchaseLineService, times(1)).addPurchaseLine(purchaseLineDTO);
    }

    @Test
    public void testUpdatePurchaseLine() {
        // Arrange
        PurchaseLineDTO purchaseLineDTO = new PurchaseLineDTO();

        // Act
        clientPurchaseController.updatePurchaseLine(1L, purchaseLineDTO);

        // Assert
        verify(purchaseLineService, times(1)).updatePurchaseLine(1L, purchaseLineDTO);
    }

    @Test
    public void testDeletePurchaseLine() {
        // Act
        clientPurchaseController.deletePurchaseLine(1L);

        // Assert
        verify(purchaseLineService, times(1)).deletePurchaseLine(1L);
    }
}
