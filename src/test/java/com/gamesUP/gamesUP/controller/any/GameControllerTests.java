package com.gamesUP.gamesUP.controller.any;

import com.gamesUP.gamesUP.DTO.Game.*;
import com.gamesUP.gamesUP.service.Game.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GameControllerTests {

    @InjectMocks
    private GameController gameController;

    @Mock
    private AuthorService authorService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private GameService gameService;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private PublisherService publisherService;

    private AuthorResponseDTO authorResponseDTO;
    private CategoryResponseDTO categoryResponseDTO;
    private GameResponseDTO gameResponseDTO;
    private InventoryResponseDTO inventoryResponseDTO;
    private PublisherResponseDTO publisherResponseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        authorResponseDTO = new AuthorResponseDTO();
        authorResponseDTO.setId(1L);
        authorResponseDTO.setName("Author Name");

        categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(1L);
        categoryResponseDTO.setType("Category Type");

        gameResponseDTO = new GameResponseDTO();
        gameResponseDTO.setId(1L);
        gameResponseDTO.setNom("Game Name");

        inventoryResponseDTO = new InventoryResponseDTO();
        inventoryResponseDTO.setId(1L);
        inventoryResponseDTO.setQuantity(10);

        publisherResponseDTO = new PublisherResponseDTO();
        publisherResponseDTO.setId(1L);
        publisherResponseDTO.setName("Publisher Name");
    }

    @Test
    public void testGetAllAuthors() {
        // Arrange
        when(authorService.getAllAuthors()).thenReturn(Arrays.asList(authorResponseDTO));

        // Act
        List<AuthorResponseDTO> result = gameController.getAllAuthors();

        // Assert
        assertEquals(1, result.size());
        assertEquals(authorResponseDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testGetAuthorById() {
        // Arrange
        when(authorService.getAuthorById(1L)).thenReturn(authorResponseDTO);

        // Act
        AuthorResponseDTO result = gameController.getAuthorById(1L);

        // Assert
        assertEquals(authorResponseDTO.getId(), result.getId());
    }

    @Test
    public void testGetAllCategories() {
        // Arrange
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(categoryResponseDTO));

        // Act
        List<CategoryResponseDTO> result = gameController.getAllCategories();

        // Assert
        assertEquals(1, result.size());
        assertEquals(categoryResponseDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testGetCategoryById() {
        // Arrange
        when(categoryService.getCategoryById(1L)).thenReturn(categoryResponseDTO);

        // Act
        CategoryResponseDTO result = gameController.getCategoryById(1L);

        // Assert
        assertEquals(categoryResponseDTO.getId(), result.getId());
    }

    @Test
    public void testGetAllGames() {
        // Arrange
        when(gameService.getAllGames()).thenReturn(Arrays.asList(gameResponseDTO));

        // Act
        List<GameResponseDTO> result = gameController.getAllJeux();

        // Assert
        assertEquals(1, result.size());
        assertEquals(gameResponseDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testGetGameById() {
        // Arrange
        when(gameService.getGameById(1L)).thenReturn(gameResponseDTO);

        // Act
        GameResponseDTO result = gameController.getGameById(1L);

        // Assert
        assertEquals(gameResponseDTO.getId(), result.getId());
    }

    @Test
    public void testGetAllInventories() {
        // Arrange
        when(inventoryService.getAllInventories()).thenReturn(Arrays.asList(inventoryResponseDTO));

        // Act
        List<InventoryResponseDTO> result = gameController.getAllInventories();

        // Assert
        assertEquals(1, result.size());
        assertEquals(inventoryResponseDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testGetInventoryById() {
        // Arrange
        when(inventoryService.getInventoryById(1L)).thenReturn(inventoryResponseDTO);

        // Act
        InventoryResponseDTO result = gameController.getInventoryById(1L);

        // Assert
        assertEquals(inventoryResponseDTO.getId(), result.getId());
    }

    @Test
    public void testGetAllPublishers() {
        // Arrange
        when(publisherService.getAllPublishers()).thenReturn(Arrays.asList(publisherResponseDTO));

        // Act
        List<PublisherResponseDTO> result = gameController.getAllPublishers();

        // Assert
        assertEquals(1, result.size());
        assertEquals(publisherResponseDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testGetPublisherById() {
        // Arrange
        when(publisherService.getPublisherById(1L)).thenReturn(publisherResponseDTO);

        // Act
        PublisherResponseDTO result = gameController.getPublisherById(1L);

        // Assert
        assertEquals(publisherResponseDTO.getId(), result.getId());
    }
}
