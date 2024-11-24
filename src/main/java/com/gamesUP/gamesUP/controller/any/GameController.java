package com.gamesUP.gamesUP.controller.any;

import com.gamesUP.gamesUP.DTO.Game.*;
import com.gamesUP.gamesUP.service.Game.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/games")
public class GameController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GameService gameService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/authors")
    public List<AuthorResponseDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/authors/{id}")
    public AuthorResponseDTO getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/categories")
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/categories/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    public List<GameResponseDTO> getAllJeux() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public GameResponseDTO getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @GetMapping("/inventories")
    public List<InventoryResponseDTO> getAllInventories() {
        return inventoryService.getAllInventories();
    }

    @GetMapping("/inventories/{id}")
    public InventoryResponseDTO getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    @GetMapping("/publishers")
    public List<PublisherResponseDTO> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("/publishers/{id}")
    public PublisherResponseDTO getPublisherById(@PathVariable Long id) {
        return publisherService.getPublisherById(id);
    }

}
