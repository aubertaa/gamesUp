package com.gamesUP.gamesUP.controller.client;

import com.gamesUP.gamesUP.DTO.Game.GameResponseDTO;
import com.gamesUP.gamesUP.service.Game.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClientGameControllerTests {

    @InjectMocks
    private ClientGameController clientGameController;

    @Mock
    private GameService gameService;

    private GameResponseDTO gameResponseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        gameResponseDTO = new GameResponseDTO();
        gameResponseDTO.setId(1L);
        gameResponseDTO.setNom("Game One");
    }

    @Test
    public void testGetGameByNom() {
        // Arrange
        String gameName = "Game One";
        when(gameService.getGamesByName(gameName)).thenReturn(Arrays.asList(gameResponseDTO));

        // Act
        List<GameResponseDTO> result = clientGameController.getGameByNom(gameName);

        // Assert
        assertEquals(1, result.size());
        assertEquals(gameResponseDTO.getNom(), result.get(0).getNom());
    }

    @Test
    public void testGetGamesByPublisher() {
        // Arrange
        String publisher = "Publisher One";
        when(gameService.getGamesByPublisher(publisher)).thenReturn(Arrays.asList(gameResponseDTO));

        // Act
        List<GameResponseDTO> result = clientGameController.getGamesByPublisher(publisher);

        // Assert
        assertEquals(1, result.size());
        assertEquals(gameResponseDTO.getNom(), result.get(0).getNom());
    }

    @Test
    public void testGetGameByCategory() {
        // Arrange
        String category = "Category One";
        when(gameService.getGamesByCategory(category)).thenReturn(Arrays.asList(gameResponseDTO));

        // Act
        List<GameResponseDTO> result = clientGameController.getGameByCategory(category);

        // Assert
        assertEquals(1, result.size());
        assertEquals(gameResponseDTO.getNom(), result.get(0).getNom());
    }

    @Test
    public void testGetGameByGenre() {
        // Arrange
        String genre = "Action";
        when(gameService.getGamesByGenre(genre)).thenReturn(Arrays.asList(gameResponseDTO));

        // Act
        List<GameResponseDTO> result = clientGameController.getGameByGenre(genre);

        // Assert
        assertEquals(1, result.size());
        assertEquals(gameResponseDTO.getNom(), result.get(0).getNom());
    }
}
