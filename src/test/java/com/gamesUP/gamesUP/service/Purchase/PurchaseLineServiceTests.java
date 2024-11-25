package com.gamesUP.gamesUP.service.Purchase;

import com.gamesUP.gamesUP.DTO.Purchase.PurchaseLineDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseLineResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityDontExistException;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Game;
import com.gamesUP.gamesUP.model.Purchase.Purchase;
import com.gamesUP.gamesUP.model.Purchase.PurchaseLine;
import com.gamesUP.gamesUP.repository.Game.GameRepository;
import com.gamesUP.gamesUP.repository.Purchase.PurchaseLineRepository;
import com.gamesUP.gamesUP.repository.Purchase.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PurchaseLineServiceTests {

    @InjectMocks
    private PurchaseLineService purchaseLineService;

    @Mock
    private PurchaseLineRepository purchaseLineRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private GameRepository gameRepository;

    private PurchaseLine purchaseLine;
    private Purchase purchase;
    private Game game;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        purchase = new Purchase();
        purchase.setId(1L);

        game = new Game();
        game.setId(1L);

        purchaseLine = new PurchaseLine();
        purchaseLine.setId(1L);
        purchaseLine.setPurchase(purchase);
        purchaseLine.setGame(game);
        purchaseLine.setPrix(29.99);
    }

    @Test
    public void testGetAllPurchaseLines() {
        // Arrange
        when(purchaseLineRepository.findAll()).thenReturn(Arrays.asList(purchaseLine));

        // Act
        List<PurchaseLineResponseDTO> result = purchaseLineService.getAllPurchaseLines();

        // Assert
        assertEquals(1, result.size());
        assertEquals(purchaseLine.getId(), result.get(0).getId());
    }

    @Test
    public void testGetPurchaseLineById() {
        // Arrange
        when(purchaseLineRepository.findById(1L)).thenReturn(Optional.of(purchaseLine));

        // Act
        PurchaseLineResponseDTO result = purchaseLineService.getPurchaseLineById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(purchaseLine.getId(), result.getId());
    }

    @Test
    public void testGetPurchaseLineByIdNotFound() {
        // Arrange
        when(purchaseLineRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        PurchaseLineResponseDTO result = purchaseLineService.getPurchaseLineById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    public void testAddPurchaseLine() {
        // Arrange
        PurchaseLineDTO purchaseLineDTO = new PurchaseLineDTO();
        purchaseLineDTO.setPurchaseId(1L);
        purchaseLineDTO.setGameId(1L);
        purchaseLineDTO.setPrix(29.99);

        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        // Act
        purchaseLineService.addPurchaseLine(purchaseLineDTO);

        // Assert
        verify(purchaseLineRepository, times(1)).save(any(PurchaseLine.class));
    }

    @Test
    public void testAddPurchaseLinePurchaseNotFound() {
        // Arrange
        PurchaseLineDTO purchaseLineDTO = new PurchaseLineDTO();
        purchaseLineDTO.setPurchaseId(1L);
        purchaseLineDTO.setGameId(1L);

        when(purchaseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityDontExistException.class, () -> purchaseLineService.addPurchaseLine(purchaseLineDTO));
    }

    @Test
    public void testAddPurchaseLineGameNotFound() {
        // Arrange
        PurchaseLineDTO purchaseLineDTO = new PurchaseLineDTO();
        purchaseLineDTO.setPurchaseId(1L);
        purchaseLineDTO.setGameId(1L);

        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityDontExistException.class, () -> purchaseLineService.addPurchaseLine(purchaseLineDTO));
    }

    @Test
    public void testUpdatePurchaseLine() {
        // Arrange
        PurchaseLineDTO purchaseLineDTO = new PurchaseLineDTO();
        purchaseLineDTO.setPurchaseId(1L);
        purchaseLineDTO.setGameId(1L);
        purchaseLineDTO.setPrix(39.99);

        when(purchaseLineRepository.findById(1L)).thenReturn(Optional.of(purchaseLine));
        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        // Act
        purchaseLineService.updatePurchaseLine(1L, purchaseLineDTO);

        // Assert
        verify(purchaseLineRepository).save(purchaseLine);
        assertEquals(39.99, purchaseLine.getPrix());
    }

    @Test
    public void testDeletePurchaseLine() {
        // Arrange
        when(purchaseLineRepository.findById(1L)).thenReturn(Optional.of(purchaseLine));

        // Act
        purchaseLineService.deletePurchaseLine(1L);

        // Assert
        verify(purchaseLineRepository).deleteById(1L);
    }

    @Test
    public void testDeletePurchaseLineDataIntegrityViolation() {
        // Arrange
        when(purchaseLineRepository.findById(1L)).thenReturn(Optional.of(purchaseLine));
        doThrow(new DataIntegrityViolationException("")).when(purchaseLineRepository).deleteById(1L);

        // Act and Assert
        assertThrows(RelationConstraintException.class, () -> purchaseLineService.deletePurchaseLine(1L));
    }
}
