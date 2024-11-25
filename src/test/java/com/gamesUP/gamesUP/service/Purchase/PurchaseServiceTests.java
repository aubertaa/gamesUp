package com.gamesUP.gamesUP.service.Purchase;

import com.gamesUP.gamesUP.DTO.Purchase.PurchaseDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityDontExistException;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Purchase.Purchase;
import com.gamesUP.gamesUP.model.User.User;
import com.gamesUP.gamesUP.repository.Purchase.PurchaseRepository;
import com.gamesUP.gamesUP.repository.User.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PurchaseServiceTests {

    @InjectMocks
    private PurchaseService purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private UserRepository userRepository;

    private Purchase purchase;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        purchase = new Purchase();
        purchase.setId(1L);
        purchase.setDate(LocalDate.now());
        purchase.setPaid(true);
        purchase.setDelivered(true);
        purchase.setArchived(false);
        purchase.setUser(user);
    }

    @Test
    public void testGetAllPurchases() {
        // Arrange
        when(purchaseRepository.findAll()).thenReturn(Arrays.asList(purchase));

        // Act
        List<PurchaseResponseDTO> result = purchaseService.getAllPurchases();

        // Assert
        assertEquals(1, result.size());
        assertEquals(purchase.getId(), result.get(0).getId());
    }

    @Test
    public void testGetPurchaseById() {
        // Arrange
        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));

        // Act
        PurchaseResponseDTO result = purchaseService.getPurchaseById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(purchase.getId(), result.getId());
    }

    @Test
    public void testGetPurchaseByIdNotFound() {
        // Arrange
        when(purchaseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        PurchaseResponseDTO result = purchaseService.getPurchaseById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    public void testAddPurchase() {
        // Arrange
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setDate(LocalDate.now());
        purchaseDTO.setPaid(true);
        purchaseDTO.setDelivered(true);
        purchaseDTO.setArchived(false);
        purchaseDTO.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        purchaseService.addPurchase(purchaseDTO);

        // Assert
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
    }

    @Test
    public void testAddPurchaseUserNotFound() {
        // Arrange
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setUserId(1L); // Non-existing user

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityDontExistException.class, () -> purchaseService.addPurchase(purchaseDTO));
    }

    @Test
    public void testUpdatePurchase() {
        // Arrange
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setDate(LocalDate.now());
        purchaseDTO.setPaid(false);
        purchaseDTO.setDelivered(false);
        purchaseDTO.setArchived(true);
        purchaseDTO.setUserId(1L);

        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        purchaseService.updatePurchase(1L, purchaseDTO);

        // Assert
        verify(purchaseRepository).save(purchase);
        assertFalse(purchase.getPaid());
        assertTrue(purchase.getArchived());
    }

    @Test
    public void testUpdatePurchaseInexistingUser() {
        // Arrange
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setDate(LocalDate.now());
        purchaseDTO.setPaid(false);
        purchaseDTO.setDelivered(false);
        purchaseDTO.setArchived(true);
        purchaseDTO.setUserId(999L);

        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Assert
        assertThrows(EntityDontExistException.class, () -> purchaseService.updatePurchase(1L, purchaseDTO));

    }

    @Test
    public void testUpdateNonExistingPurchase() {
        // Arrange
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setDate(LocalDate.now());
        purchaseDTO.setPaid(false);
        purchaseDTO.setDelivered(false);
        purchaseDTO.setArchived(true);
        purchaseDTO.setUserId(1L);

        when(purchaseRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        purchaseService.updatePurchase(1L, purchaseDTO);

        // Assert
        verify(purchaseRepository, never()).save(purchase);
    }


    @Test
    public void testDeletePurchase() {
        // Arrange
        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));

        // Act
        purchaseService.deletePurchase(1L);

        // Assert
        verify(purchaseRepository).deleteById(1L);
    }

    @Test
    public void testDeletePurchaseDataIntegrityViolation() {
        // Arrange
        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        doThrow(new DataIntegrityViolationException("")).when(purchaseRepository).deleteById(1L);

        // Act and Assert
        assertThrows(RelationConstraintException.class, () -> purchaseService.deletePurchase(1L));
    }
}
