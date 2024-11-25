package com.gamesUP.gamesUP.service.User;

import com.gamesUP.gamesUP.DTO.User.WishlistDTO;
import com.gamesUP.gamesUP.DTO.User.WishlistResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityDontExistException;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Game;
import com.gamesUP.gamesUP.model.User.User;
import com.gamesUP.gamesUP.model.User.Wishlist;
import com.gamesUP.gamesUP.repository.Game.GameRepository;
import com.gamesUP.gamesUP.repository.User.UserRepository;
import com.gamesUP.gamesUP.repository.User.WishlistRepository;
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

public class WishlistServiceTests {

    @InjectMocks
    private WishlistService wishlistService;

    @Mock
    private WishlistRepository wishListRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    private Wishlist wishlist;
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

        wishlist = new Wishlist();
        wishlist.setId(1L);
        wishlist.setUser(user);
        wishlist.setGame(game);
    }

    @Test
    public void testGetAllWishlists() {
        // Arrange
        when(wishListRepository.findAll()).thenReturn(Arrays.asList(wishlist));

        // Act
        var result = wishlistService.getAllWishlists();

        // Assert
        assertEquals(1, result.size());
        assertEquals(wishlist.getId(), result.get(0).getId());
    }

    @Test
    public void testGetWishlistById() {
        // Arrange
        when(wishListRepository.findById(1L)).thenReturn(Optional.of(wishlist));

        // Act
        WishlistResponseDTO result = wishlistService.getWishlistById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(wishlist.getId(), result.getId());
    }

    @Test
    public void testGetWishlistByIdNotFound() {
        // Arrange
        when(wishListRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        WishlistResponseDTO result = wishlistService.getWishlistById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    public void testAddWishlist() {
        // Arrange
        WishlistDTO wishlistDTO = new WishlistDTO();
        wishlistDTO.setUserId(1L);
        wishlistDTO.setGameId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        // Act
        wishlistService.addWishlist(wishlistDTO);

        // Assert
        verify(wishListRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    public void testAddWishlistUserNotFound() {
        // Arrange
        WishlistDTO wishlistDTO = new WishlistDTO();
        wishlistDTO.setUserId(1L);
        wishlistDTO.setGameId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityDontExistException.class, () -> wishlistService.addWishlist(wishlistDTO));
    }

    @Test
    public void testAddWishlistGameNotFound() {
        // Arrange
        WishlistDTO wishlistDTO = new WishlistDTO();
        wishlistDTO.setUserId(1L);
        wishlistDTO.setGameId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityDontExistException.class, () -> wishlistService.addWishlist(wishlistDTO));
    }

    @Test
    public void testUpdateWishlist() {
        // Arrange
        WishlistDTO wishlistDTO = new WishlistDTO();
        wishlistDTO.setUserId(1L);
        wishlistDTO.setGameId(1L);

        when(wishListRepository.findById(1L)).thenReturn(Optional.of(wishlist));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        // Act
        wishlistService.updateWishlist(1L, wishlistDTO);

        // Assert
        verify(wishListRepository).save(wishlist);
    }


    @Test
    public void testDeleteWishlist() {
        // Arrange
        when(wishListRepository.findById(1L)).thenReturn(Optional.of(wishlist));

        // Act
        wishlistService.deleteWishlist(1L);

        // Assert
        verify(wishListRepository).deleteById(1L);
    }

    @Test
    public void testDeleteWishlistDataIntegrityViolation() {
        // Arrange
        when(wishListRepository.findById(1L)).thenReturn(Optional.of(wishlist));
        doThrow(new DataIntegrityViolationException("")).when(wishListRepository).deleteById(1L);

        // Act and Assert
        assertThrows(RelationConstraintException.class, () -> wishlistService.deleteWishlist(1L));
    }
}
