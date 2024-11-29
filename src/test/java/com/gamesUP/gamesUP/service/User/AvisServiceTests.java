package com.gamesUP.gamesUP.service.User;

import com.gamesUP.gamesUP.DTO.User.AvisDTO;
import com.gamesUP.gamesUP.DTO.User.AvisResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityDontExistException;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Game;
import com.gamesUP.gamesUP.model.User.Avis;
import com.gamesUP.gamesUP.model.User.User;
import com.gamesUP.gamesUP.repository.Game.GameRepository;
import com.gamesUP.gamesUP.repository.User.AvisRepository;
import com.gamesUP.gamesUP.repository.User.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AvisServiceTests {

    @InjectMocks
    private AvisService avisService;

    @Mock
    private AvisRepository avisRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    private Avis avis;
    private User user;
    private Game game;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setNom("John Doe");

        game = new Game();
        game.setId(1L);

        avis = new Avis();
        avis.setId(1L);
        avis.setUser(user);
        avis.setGame(game);
        avis.setCommentaire("Great game!");
        avis.setNote(5f);
    }

    @Test
    public void testGetAllAvis() {
        // Arrange
        when(avisRepository.findAll()).thenReturn(Arrays.asList(avis));

        // Act
        var result = avisService.getAllAvis();

        // Assert
        assertEquals(1, result.size());
        assertEquals(avis.getId(), result.get(0).getId());
    }

    @Test
    public void testGetAvisById() {
        // Arrange
        when(avisRepository.findById(1L)).thenReturn(Optional.of(avis));

        // Act
        AvisResponseDTO result = avisService.getAvisById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(avis.getId(), result.getId());
    }

    @Test
    public void testGetAvisByIdNotFound() {
        // Arrange
        when(avisRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        AvisResponseDTO result = avisService.getAvisById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    public void testAddAvis() {
        // Arrange
        AvisDTO avisDTO = new AvisDTO();
        avisDTO.setUserId(1L);
        avisDTO.setGameId(1L);
        avisDTO.setCommentaire("Great game!");
        avisDTO.setNote(5f);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        // Act
        avisService.addAvis(avisDTO);

        // Assert
        verify(avisRepository, times(1)).save(any(Avis.class));
    }

    @Test
    public void testAddAvisUserNotFound() {
        // Arrange
        AvisDTO avisDTO = new AvisDTO();
        avisDTO.setUserId(1L);
        avisDTO.setGameId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityDontExistException.class, () -> avisService.addAvis(avisDTO));
    }

    @Test
    public void testAddAvisGameNotFound() {
        // Arrange
        AvisDTO avisDTO = new AvisDTO();
        avisDTO.setUserId(1L);
        avisDTO.setGameId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityDontExistException.class, () -> avisService.addAvis(avisDTO));
    }

    @Test
    public void testUpdateAvis() {
        // Arrange
        AvisDTO avisDTO = new AvisDTO();
        avisDTO.setUserId(1L);
        avisDTO.setGameId(1L);
        avisDTO.setCommentaire("Updated Comment");
        avisDTO.setNote(4f);

        when(avisRepository.findById(1L)).thenReturn(Optional.of(avis));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        // Act
        avisService.updateAvis(1L, avisDTO);

        // Assert
        verify(avisRepository).save(avis);
        assertEquals("Updated Comment", avis.getCommentaire());
        assertEquals(4, avis.getNote());
    }

    @Test
    public void testDeleteAvis() {
        // Arrange
        when(avisRepository.findById(1L)).thenReturn(Optional.of(avis));

        // Act
        avisService.deleteAvis(1L);

        // Assert
        verify(avisRepository).deleteById(1L);
    }

    @Test
    public void testDeleteAvisDataIntegrityViolation() {
        // Arrange
        when(avisRepository.findById(1L)).thenReturn(Optional.of(avis));
        doThrow(new DataIntegrityViolationException("")).when(avisRepository).deleteById(1L);

        // Act and Assert
        assertThrows(RelationConstraintException.class, () -> avisService.deleteAvis(1L));
    }
}
