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
import static org.mockito.Mockito.*;

public class GameServiceTests {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private GameService gameService;

    private Game game1;
    private Game game2;
    private Author author;
    private Category category;
    private Publisher publisher;

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
    }

    @Test
    public void testGetAllGames() {
        // Arrange
        when(gameRepository.findAll()).thenReturn(Arrays.asList(game1, game2));

        // Act
        List<GameResponseDTO> result = gameService.getAllGames();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testAddGame() {
        // Arrange
        GameDTO gameDTO = new GameDTO();
        gameDTO.setNom("New Game");
        gameDTO.setGenre("New Genre");
        gameDTO.setNumEdition("1");
        gameDTO.setAuthorName("Author One");
        gameDTO.setCategoryType("Category One");
        gameDTO.setPublisherName("Publisher One");

        when(authorRepository.findByName("Author One")).thenReturn(Optional.of(author));
        when(categoryRepository.findByType("Category One")).thenReturn(Optional.of(category));
        when(publisherRepository.findByName("Publisher One")).thenReturn(Optional.of(publisher));

        // Act
        gameService.addGame(gameDTO);

        // Assert
        verify(gameRepository).save(any(Game.class));
    }

    @Test
    public void testAddGameAuthorNotFound() {
        // Arrange
        GameDTO gameDTO = new GameDTO();
        gameDTO.setAuthorName("Unknown Author");

        when(authorRepository.findByName("Unknown Author")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityDontExistException.class, () -> gameService.addGame(gameDTO));
    }

    @Test
    public void testAddGameCategoryNotFound() {
        // Arrange
        GameDTO gameDTO = new GameDTO();
        gameDTO.setCategoryType("Unknown category");

        when(authorRepository.findByName("Unknown category")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityDontExistException.class, () -> gameService.addGame(gameDTO));
    }

    @Test
    public void testAddGamePublisherNotFound() {
        // Arrange
        GameDTO gameDTO = new GameDTO();
        gameDTO.setCategoryType("Unknown publisher");

        when(authorRepository.findByName("Unknown publisher")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityDontExistException.class, () -> gameService.addGame(gameDTO));
    }

    @Test
    public void testUpdateGame() {
        // Arrange
        Long gameId = 1L;
        GameDTO gameDTO = new GameDTO();
        gameDTO.setNom("Updated Game");
        gameDTO.setGenre("Updated Genre");
        gameDTO.setNumEdition("1");
        gameDTO.setAuthorName("Author One");
        gameDTO.setCategoryType("Category One");
        gameDTO.setPublisherName("Publisher One");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game1));
        when(authorRepository.findByName("Author One")).thenReturn(Optional.of(author));
        when(categoryRepository.findByType("Category One")).thenReturn(Optional.of(category));
        when(publisherRepository.findByName("Publisher One")).thenReturn(Optional.of(publisher));

        // Act
        gameService.updateGame(gameId, gameDTO);

        // Assert
        assertEquals("Updated Game", game1.getNom());
        verify(gameRepository).save(game1);
    }


    @Test
    public void testUpdateGameAuthorNotFound() {
        // Arrange
        Long gameId = 1L;
        GameDTO gameDTO = new GameDTO();
        gameDTO.setNom("Updated Game");
        gameDTO.setGenre("Updated Genre");
        gameDTO.setNumEdition("1");
        gameDTO.setAuthorName("Author not found");
        gameDTO.setCategoryType("Category One");
        gameDTO.setPublisherName("Publisher One");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game1));
        when(authorRepository.findByName("Author not found")).thenReturn(Optional.empty());
        when(categoryRepository.findByType("Category One")).thenReturn(Optional.of(category));
        when(publisherRepository.findByName("Publisher One")).thenReturn(Optional.of(publisher));

        // Assert
        assertThrows(EntityDontExistException.class, () -> gameService.updateGame(gameId, gameDTO));
    }


    @Test
    public void testUpdateGameCategoryNotFound() {
        // Arrange
        Long gameId = 1L;
        GameDTO gameDTO = new GameDTO();
        gameDTO.setNom("Updated Game");
        gameDTO.setGenre("Updated Genre");
        gameDTO.setNumEdition("1");
        gameDTO.setAuthorName("Author One");
        gameDTO.setCategoryType("Category not found");
        gameDTO.setPublisherName("Publisher One");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game1));
        when(authorRepository.findByName("Author One")).thenReturn(Optional.of(author));
        when(categoryRepository.findByType("Category not found")).thenReturn(Optional.empty());
        when(publisherRepository.findByName("Publisher One")).thenReturn(Optional.of(publisher));

        // Assert
        assertThrows(EntityDontExistException.class, () -> gameService.updateGame(gameId, gameDTO));

    }


    @Test
    public void testUpdateGamePublisherNotFound() {
        // Arrange
        Long gameId = 1L;
        GameDTO gameDTO = new GameDTO();
        gameDTO.setNom("Updated Game");
        gameDTO.setGenre("Updated Genre");
        gameDTO.setNumEdition("1");
        gameDTO.setAuthorName("Author One");
        gameDTO.setCategoryType("Category One");
        gameDTO.setPublisherName("Publisher not found");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game1));
        when(authorRepository.findByName("Author One")).thenReturn(Optional.of(author));
        when(categoryRepository.findByType("Category One")).thenReturn(Optional.of(category));
        when(publisherRepository.findByName("Publisher not found")).thenReturn(Optional.empty());

        // Assert
        assertThrows(EntityDontExistException.class, () -> gameService.updateGame(gameId, gameDTO));

    }


    @Test
    public void testDeleteGame() {
        // Arrange
        Long gameId = 1L;
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game1));

        // Act
        gameService.deleteGame(gameId);

        // Assert
        verify(gameRepository).deleteById(gameId);
    }

    @Test
    public void testDeleteGameDataIntegrityViolation() {
        // Arrange
        Long gameId = 1L;
        doThrow(new DataIntegrityViolationException("")).when(gameRepository).deleteById(gameId);

        // Act and Assert
        assertThrows(RelationConstraintException.class, () -> gameService.deleteGame(gameId));
    }

    @Test
    public void testGetGamesByName() {
        // Arrange
        when(gameRepository.findByName("Game One")).thenReturn(Arrays.asList(game1));

        // Act
        List<GameResponseDTO> result = gameService.getGamesByName("Game One");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Game One", result.get(0).getNom());
    }

    @Test
    public void testGetGamesByGenre() {
        // Arrange
        when(gameRepository.findByGenre("Genre One")).thenReturn(Arrays.asList(game1));

        // Act
        List<GameResponseDTO> result = gameService.getGamesByGenre("Genre One");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Genre One", result.get(0).getGenre());
    }

    @Test
    public void testGetGamesByPublisher() {
        // Arrange
        when(gameRepository.findByPublisherName(publisher.getName())).thenReturn(Arrays.asList(game1, game2));

        // Act
        List<GameResponseDTO> result = gameService.getGamesByPublisher("Publisher One");

        // Assert
        assertEquals(2, result.size());
        assertEquals("Publisher One", result.get(0).getPublisherName());
    }

    @Test
    public void testGetGamesByCategory() {
        // Arrange
        when(gameRepository.findByCategoryType(category.getType())).thenReturn(Arrays.asList(game1, game2));

        // Act
        List<GameResponseDTO> result = gameService.getGamesByCategory("Category One");

        // Assert
        assertEquals(2, result.size());
        assertEquals("Category One", result.get(0).getCategoryType());
    }

    @Test
    public void testGetGameById() {

        // Arrange
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game1));

        // Act
        GameResponseDTO result = gameService.getGameById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(game1.getGenre(), result.getGenre());
        assertEquals(game1.getId(), result.getId());
        assertEquals(game1.getNom(), result.getNom());
        assertEquals(game1.getPublisher().getName(), result.getPublisherName());

    }

}
