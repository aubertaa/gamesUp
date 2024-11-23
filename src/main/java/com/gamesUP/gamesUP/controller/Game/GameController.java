package com.gamesUP.gamesUP.controller.Game;

import com.gamesUP.gamesUP.DTO.Game.GameDTO;
import com.gamesUP.gamesUP.DTO.Game.GameResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.Game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping
    public List<GameResponseDTO> getAllJeux() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public GameResponseDTO getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

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

}
