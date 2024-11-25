package com.gamesUP.gamesUP.controller.client;

import com.gamesUP.gamesUP.DTO.User.AvisDTO;
import com.gamesUP.gamesUP.DTO.User.UserDTO;
import com.gamesUP.gamesUP.DTO.User.WishlistDTO;
import com.gamesUP.gamesUP.DTO.User.WishlistResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.User.AvisService;
import com.gamesUP.gamesUP.service.User.UserService;
import com.gamesUP.gamesUP.service.User.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ClientUserControllerTests {

    @InjectMocks
    private ClientUserController clientUserController;

    @Mock
    private UserService userService;

    @Mock
    private WishlistService wishListService;

    @Mock
    private AvisService avisService;

    private AvisDTO avisDTO;
    private UserDTO userDTO;
    private WishlistDTO wishlistDTO;
    private WishlistResponseDTO wishlistResponseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        avisDTO = new AvisDTO();
        userDTO = new UserDTO();
        wishlistDTO = new WishlistDTO();

        wishlistResponseDTO = new WishlistResponseDTO();
        wishlistResponseDTO.setId(1L);
    }

    @Test
    public void testAddAvis() {
        // Act
        clientUserController.addAvis(avisDTO);

        // Assert
        verify(avisService).addAvis(avisDTO);
    }

    @Test
    public void testUpdateAvis() {
        Long avisId = 1L;

        // Act
        clientUserController.updateAvis(avisId, avisDTO);

        // Assert
        verify(avisService).updateAvis(avisId, avisDTO);
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;

        // Act
        clientUserController.updateUser(userId, userDTO);

        // Assert
        verify(userService).updateUser(userId, userDTO);
    }

    @Test
    public void testGetWishlistById() {
        Long wishlistId = 1L;

        // Arrange
        when(wishListService.getWishlistById(wishlistId)).thenReturn(wishlistResponseDTO);

        // Act
        WishlistResponseDTO result = clientUserController.getWishlistById(wishlistId);

        // Assert
        assertEquals(wishlistResponseDTO.getId(), result.getId());
        verify(wishListService).getWishlistById(wishlistId);
    }

    @Test
    public void testAddWishlist() {
        // Act
        clientUserController.addWishlist(wishlistDTO);

        // Assert
        verify(wishListService).addWishlist(wishlistDTO);
    }

    @Test
    public void testAddWishlistAlreadyExists() {
        // Arrange
        doThrow(new EntityAlreadyExistException("wishlist already exists for this user"))
                .when(wishListService).addWishlist(any(WishlistDTO.class));

        // Act and Assert
        Exception exception = assertThrows(EntityAlreadyExistException.class, () -> {
            clientUserController.addWishlist(wishlistDTO);
        });

        // Verify the exception message
        assertEquals("wishlist already exists for this user", exception.getMessage());
    }

    @Test
    public void testUpdateWishlist() {
        Long wishlistId = 1L;

        // Act
        clientUserController.updateWishlist(wishlistId, wishlistDTO);

        // Assert
        verify(wishListService).updateWishlist(wishlistId, wishlistDTO);
    }

    @Test
    public void testDeleteWishlist() {
        Long wishlistId = 1L;

        // Act
        clientUserController.deleteWishlist(wishlistId);

        // Assert
        verify(wishListService).deleteWishlist(wishlistId);
    }
}
