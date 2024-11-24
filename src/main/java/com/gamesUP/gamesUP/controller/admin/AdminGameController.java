package com.gamesUP.gamesUP.controller.admin;

import com.gamesUP.gamesUP.DTO.Game.*;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.Game.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/games")
public class AdminGameController {
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

    @PostMapping("/authors")
    public void addAuthor(@RequestBody AuthorDTO authorDTO) {
        try {
            authorService.addAuthor(authorDTO);
        } catch (Exception e) {
            throw new EntityAlreadyExistException("Author already exists");
        }
    }
    
    @PutMapping("/authors/{id}")
    public void updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        authorService.updateAuthor(id, authorDTO);
    }
    
    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

    @PostMapping("/categories")
    public void addCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            categoryService.addCategory(categoryDTO);
        } catch (Exception e) {
            throw new EntityAlreadyExistException("Category already exists");
        }
    }
    
    @PutMapping("/categories/{id}")
    public void updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @PostMapping
    public void ajouterJeu(@RequestBody GameDTO gameDTO) {
        try {
            gameService.addGame(gameDTO);
        } catch (Exception e) {
            throw new EntityAlreadyExistException("Game already exists");
        }
    }

    @PutMapping("/{id}")
    public void updateGame(@PathVariable Long id, @RequestBody GameDTO gameDTO) {
        gameService.updateGame(id, gameDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }

    
    @PostMapping("/inventories")
    public void addInventory(@RequestBody InventoryDTO inventoryDTO) {
        inventoryService.addInventory(inventoryDTO);
    }

    @PutMapping("/inventories/{id}")
    public void updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {
        inventoryService.updateInventory(id, inventoryDTO);
    }
    
    @DeleteMapping("/inventories/{id}")
    public void deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
    }

    @PostMapping("/publishers")
    public void addPublisher(@RequestBody PublisherDTO publisherDTO) {
        try {
            publisherService.addPublisher(publisherDTO);
        } catch (Exception e) {
            throw new EntityAlreadyExistException("Publisher already exists");
        }
    }
    
    @PutMapping("/publishers/{id}")
    public void updatePublisher(@PathVariable Long id, @RequestBody PublisherDTO publisherDTO) {
        publisherService.updatePublisher(id, publisherDTO);
    }

    @DeleteMapping("/publishers/{id}")
    public void deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
    }

}
