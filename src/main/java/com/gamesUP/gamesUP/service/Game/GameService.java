package com.gamesUP.gamesUP.service.Game;

import com.gamesUP.gamesUP.DTO.Game.GameDTO;
import com.gamesUP.gamesUP.DTO.Game.GameResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityDontExistException;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Author;
import com.gamesUP.gamesUP.model.Game.Category;
import com.gamesUP.gamesUP.model.Game.Game;
import com.gamesUP.gamesUP.model.Game.Publisher;
import com.gamesUP.gamesUP.repository.Game.AuthorRepository;
import com.gamesUP.gamesUP.repository.Game.CategoryRepository;
import com.gamesUP.gamesUP.repository.Game.GameRepository;
import com.gamesUP.gamesUP.repository.Game.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;


    public List<GameResponseDTO> getAllGames() {
        return gameRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void addGame(GameDTO gameDTO) {

        // Check if Author exists
        Author author = authorRepository.findByName(gameDTO.getAuthorName())
                .orElseThrow(() -> new EntityDontExistException("Author not found with name: " + gameDTO.getAuthorName()));

        // Check if category exists
        Category category = categoryRepository.findByType(gameDTO.getCategoryType())
                .orElseThrow(() -> new EntityDontExistException("Category not found with type: " + gameDTO.getCategoryType()));

        // Check if publisher exists
        Publisher publisher = publisherRepository.findByName(gameDTO.getPublisherName())
                .orElseThrow(() -> new EntityDontExistException("Publisher not found with name: " + gameDTO.getPublisherName()));

        Game game = new Game();
        game.setNom(gameDTO.getNom());
        game.setGenre(gameDTO.getGenre());
        game.setNumEdition(gameDTO.getNumEdition());
        game.setAuthor(author);
        game.setCategory(category);
        game.setPublisher(publisher);
        game.setPrix(gameDTO.getPrix());
        gameRepository.save(game);
    }

    public void updateGame(Long id, GameDTO gameDTO) {

        // Check if Game exists
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Game not found with ID: " + id));

        // Check if Author exists
        Author author = authorRepository.findByName(gameDTO.getAuthorName())
                .orElseThrow(() -> new EntityDontExistException("Author not found with name: " + gameDTO.getAuthorName()));

        // Check if category exists
        Category category = categoryRepository.findByType(gameDTO.getCategoryType())
                .orElseThrow(() -> new EntityDontExistException("Category not found with type: " + gameDTO.getCategoryType()));

        // Check if publisher exists
        Publisher publisher = publisherRepository.findByName(gameDTO.getPublisherName())
                .orElseThrow(() -> new EntityDontExistException("Publisher not found with name: " + gameDTO.getPublisherName()));

        game.setNom(gameDTO.getNom());
        game.setGenre(gameDTO.getGenre());
        game.setNumEdition(gameDTO.getNumEdition());
        game.setAuthor(author);
        game.setCategory(category);
        game.setPublisher(publisher);
        game.setPrix(gameDTO.getPrix());
        gameRepository.save(game);
    }

    public void deleteGame(Long id) {
        try {
            gameRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new RelationConstraintException("Cannot delete game, related entities exist.");
        }
    }

    private GameResponseDTO convertToDTO(Game game) {
        GameResponseDTO dto = new GameResponseDTO();
        dto.setId(game.getId());
        dto.setNom(game.getNom());
        dto.setGenre(game.getGenre());
        dto.setNumEdition(game.getNumEdition());
        dto.setAuthorName(game.getAuthor().getName());
        dto.setCategoryType(game.getCategory().getType());
        dto.setPublisherName(game.getPublisher().getName());
        dto.setPrix(game.getPrix());
        return dto;
    }

    public GameResponseDTO getGameById(Long id) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        return gameOptional.map(this::convertToDTO).orElse(null);
    }

    public List<GameResponseDTO> getGamesByName(String name) {
        List<Game> games = gameRepository.findByName(name);
        return games.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<GameResponseDTO> getGamesByGenre(String genre) {
        List<Game> games = gameRepository.findByGenre(genre);
        return games.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<GameResponseDTO> getGamesByPublisher(String publisher) {
        List<Game> games = gameRepository.findByPublisherName(publisher);
        return games.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<GameResponseDTO> getGamesByCategory(String category) {
        List<Game> games = gameRepository.findByCategoryType(category);
        return games.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
