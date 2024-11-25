package com.gamesUP.gamesUP.controller.admin;

import com.gamesUP.gamesUP.DTO.Purchase.PurchaseLineResponseDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseResponseDTO;
import com.gamesUP.gamesUP.service.Purchase.PurchaseLineService;
import com.gamesUP.gamesUP.service.Purchase.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdminPurchaseControllerTests {

    @InjectMocks
    private AdminPurchaseController adminPurchaseController;

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
        // Set other properties as needed

        purchaseLineResponseDTO = new PurchaseLineResponseDTO();
        purchaseLineResponseDTO.setId(1L);
        // Set other properties as needed
    }

    @Test
    public void testGetAllPurchases() {
        // Arrange
        List<PurchaseResponseDTO> purchaseList = Arrays.asList(purchaseResponseDTO);
        when(purchaseService.getAllPurchases()).thenReturn(purchaseList);

        // Act
        List<PurchaseResponseDTO> result = adminPurchaseController.getAllPurchases();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(purchaseResponseDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testDeletePurchase() {
        // Arrange
        Long purchaseId = 1L;

        // Act
        adminPurchaseController.deletePurchase(purchaseId);

        // Assert
        verify(purchaseService).deletePurchase(purchaseId);
    }

    @Test
    public void testGetAllPurchaseLines() {
        // Arrange
        List<PurchaseLineResponseDTO> purchaseLineList = Arrays.asList(purchaseLineResponseDTO);
        when(purchaseLineService.getAllPurchaseLines()).thenReturn(purchaseLineList);

        // Act
        List<PurchaseLineResponseDTO> result = adminPurchaseController.getAllPurchaseLines();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(purchaseLineResponseDTO.getId(), result.get(0).getId());
    }
}
