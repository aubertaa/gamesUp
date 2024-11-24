package com.gamesUP.gamesUP.controller.client;

import com.gamesUP.gamesUP.DTO.Game.*;
import com.gamesUP.gamesUP.service.Game.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client/games")
public class ClientGameController {
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
    
    @GetMapping("/search/name")
    public List<GameResponseDTO> getGameByNom(@RequestParam String nom) {
        return gameService.getGamesByName(nom);
    }

    
    @GetMapping("/search/publisher")
    public List<GameResponseDTO> getGamesByPublisher(@RequestParam String publisher) {
        return gameService.getGamesByPublisher(publisher);
    }

    
    @GetMapping("/search/category")
    public List<GameResponseDTO> getGameByCategory(@RequestParam String category) {
        return gameService.getGamesByCategory(category);
    }

    
    @GetMapping("/search/genre")
    public List<GameResponseDTO> getGameByGenre(@RequestParam String genre) {
        return gameService.getGamesByGenre(genre);
    }

}
