package com.gamesUP.gamesUP.controller.admin;

import com.gamesUP.gamesUP.DTO.Game.*;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.Game.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class AdminGameControllerTests {

    @InjectMocks
    private AdminGameController adminGameController;

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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests for Author Management
    @Test
    public void testAddAuthor() {
        AuthorDTO authorDTO = new AuthorDTO();
        // Set properties on authorDTO as needed

        adminGameController.addAuthor(authorDTO);

        verify(authorService).addAuthor(authorDTO);
    }

    @Test
    public void testAddAuthorAlreadyExists() {
        AuthorDTO authorDTO = new AuthorDTO();
        // Set properties on authorDTO as needed

        doThrow(new EntityAlreadyExistException("Author already exists")).when(authorService).addAuthor(any(AuthorDTO.class));

        try {
            adminGameController.addAuthor(authorDTO);
        } catch (EntityAlreadyExistException e) {
            assertEquals("Author already exists", e.getMessage());
        }

        verify(authorService).addAuthor(authorDTO);
    }

    @Test
    public void testUpdateAuthor() {
        Long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();

        adminGameController.updateAuthor(authorId, authorDTO);

        verify(authorService).updateAuthor(authorId, authorDTO);
    }

    @Test
    public void testDeleteAuthor() {
        Long authorId = 1L;

        adminGameController.deleteAuthor(authorId);

        verify(authorService).deleteAuthor(authorId);
    }

    // Tests for Category Management
    @Test
    public void testAddCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        // Set properties on categoryDTO as needed

        adminGameController.addCategory(categoryDTO);

        verify(categoryService).addCategory(categoryDTO);
    }

    @Test
    public void testAddCategoryAlreadyExists() {
        CategoryDTO categoryDTO = new CategoryDTO();
        // Set properties on categoryDTO as needed

        doThrow(new EntityAlreadyExistException("Category already exists")).when(categoryService).addCategory(any(CategoryDTO.class));

        try {
            adminGameController.addCategory(categoryDTO);
        } catch (EntityAlreadyExistException e) {
            assertEquals("Category already exists", e.getMessage());
        }

        verify(categoryService).addCategory(categoryDTO);
    }

    @Test
    public void testUpdateCategory() {
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();

        adminGameController.updateCategory(categoryId, categoryDTO);

        verify(categoryService).updateCategory(categoryId, categoryDTO);
    }

    @Test
    public void testDeleteCategory() {
        Long categoryId = 1L;

        adminGameController.deleteCategory(categoryId);

        verify(categoryService).deleteCategory(categoryId);
    }

    // Tests for Game Management
    @Test
    public void testAddGame() {
        GameDTO gameDTO = new GameDTO();

        adminGameController.ajouterJeu(gameDTO);

        verify(gameService).addGame(gameDTO);
    }

    @Test
    public void testAddGameAlreadyExists() {
        GameDTO gameDTO = new GameDTO();

        doThrow(new EntityAlreadyExistException("Game already exists")).when(gameService).addGame(any(GameDTO.class));

        try {
            adminGameController.ajouterJeu(gameDTO);
        } catch (EntityAlreadyExistException e) {
            assertEquals("Game already exists", e.getMessage());
        }

        verify(gameService).addGame(gameDTO);
    }

    @Test
    public void testUpdateGame() {
        Long gameId = 1L;
        GameDTO gameDTO = new GameDTO();

        adminGameController.updateGame(gameId, gameDTO);

        verify(gameService).updateGame(gameId, gameDTO);
    }

    @Test
    public void testDeleteGame() {
        Long gameId = 1L;

        adminGameController.deleteGame(gameId);

        verify(gameService).deleteGame(gameId);
    }

    // Tests for Inventory Management
    @Test
    public void testAddInventory() {
        InventoryDTO inventoryDTO = new InventoryDTO();

        adminGameController.addInventory(inventoryDTO);

        verify(inventoryService).addInventory(inventoryDTO);
    }

    @Test
    public void testUpdateInventory() {
        Long inventoryId = 1L;
        InventoryDTO inventoryDTO = new InventoryDTO();

        adminGameController.updateInventory(inventoryId, inventoryDTO);

        verify(inventoryService).updateInventory(inventoryId, inventoryDTO);
    }

    @Test
    public void testDeleteInventory() {
        Long inventoryId = 1L;

        adminGameController.deleteInventory(inventoryId);

        verify(inventoryService).deleteInventory(inventoryId);
    }

    // Tests for Publisher Management
    @Test
    public void testAddPublisher() {
        PublisherDTO publisherDTO = new PublisherDTO();

        adminGameController.addPublisher(publisherDTO);

        verify(publisherService).addPublisher(publisherDTO);
    }

    @Test
    public void testAddPublisherAlreadyExists() {
        PublisherDTO publisherDTO = new PublisherDTO();

        doThrow(new EntityAlreadyExistException("Publisher already exists")).when(publisherService).addPublisher(any(PublisherDTO.class));

        try {
            adminGameController.addPublisher(publisherDTO);
        } catch (EntityAlreadyExistException e) {
            assertEquals("Publisher already exists", e.getMessage());
        }

        verify(publisherService).addPublisher(publisherDTO);
    }

    @Test
    public void testUpdatePublisher() {
        Long publisherId = 1L;
        PublisherDTO publisherDTO = new PublisherDTO();

        adminGameController.updatePublisher(publisherId, publisherDTO);

        verify(publisherService).updatePublisher(publisherId, publisherDTO);
    }

    @Test
    public void testDeletePublisher() {
        Long publisherId = 1L;

        adminGameController.deletePublisher(publisherId);

        verify(publisherService).deletePublisher(publisherId);
    }
}
