package com.gamesUP.gamesUP.service.Game;

import com.gamesUP.gamesUP.DTO.Game.InventoryDTO;
import com.gamesUP.gamesUP.DTO.Game.InventoryResponseDTO;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.*;
import com.gamesUP.gamesUP.repository.Game.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class InventoryServiceTests {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Game game1;
    private Game game2;
    private Author author;
    private Category category;
    private Publisher publisher;
    private Inventory inventory1;
    private Inventory inventory2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        author = new Author();
        author.setId(1L);
        author.setName("Author One");

        category = new Category();
        category.setId(1L);
        category.setType("Category One");

        publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("Publisher One");

        game1 = new Game();
        game1.setId(1L);
        game1.setNom("Game One");
        game1.setGenre("Genre One");
        game1.setNumEdition("1");
        game1.setAuthor(author);
        game1.setCategory(category);
        game1.setPublisher(publisher);

        game2 = new Game();
        game2.setId(2L);
        game2.setNom("Game Two");
        game2.setGenre("Genre Two");
        game2.setNumEdition("2");
        game2.setAuthor(author);
        game2.setCategory(category);
        game2.setPublisher(publisher);

        inventory1 = new Inventory();
        inventory1.setId(1L);
        inventory1.setGame(game1);
        inventory1.setQuantity(10);

        inventory2 = new Inventory();
        inventory2.setId(2L);
        inventory2.setGame(game2);
        inventory2.setQuantity(20);
    }

    @Test
    public void testGetAllInventories() {
        // Arrange
        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(inventory1, inventory2));

        // Act
        List<InventoryResponseDTO> result = inventoryService.getAllInventories();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testAddInventory() {
        // Arrange
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setQuantity(15);

        // Act
        inventoryService.addInventory(inventoryDTO);

        // Assert
        verify(inventoryRepository).save(any(Inventory.class));
    }

    @Test
    public void testUpdateInventory() {
        // Arrange
        Long inventoryId = 1L;
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setQuantity(30);
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(inventory1));

        // Act
        inventoryService.updateInventory(inventoryId, inventoryDTO);

        // Assert
        assertEquals(30, inventory1.getQuantity());
        verify(inventoryRepository).save(inventory1);
    }

    @Test
    public void testDeleteInventory() {
        // Arrange
        Long inventoryId = 1L;

        // Act
        inventoryService.deleteInventory(inventoryId);

        // Assert
        verify(inventoryRepository).deleteById(inventoryId);
    }

    @Test
    public void testDeleteInventoryDataIntegrityViolation() {
        // Arrange
        Long inventoryId = 1L;
        doThrow(new DataIntegrityViolationException("")).when(inventoryRepository).deleteById(inventoryId);

        // Act and Assert
        assertThrows(RelationConstraintException.class, () -> inventoryService.deleteInventory(inventoryId));
    }
}
