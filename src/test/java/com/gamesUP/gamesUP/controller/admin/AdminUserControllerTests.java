package com.gamesUP.gamesUP.controller.admin;

import com.gamesUP.gamesUP.DTO.User.AvisResponseDTO;
import com.gamesUP.gamesUP.DTO.User.UserDTO;
import com.gamesUP.gamesUP.DTO.User.UserResponseDTO;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminUserControllerTests {

    @InjectMocks
    private AdminUserController adminUserController;

    @Mock
    private UserService userService;

    @Mock
    private WishlistService wishListService;

    @Mock
    private AvisService avisService;

    private UserResponseDTO userResponseDTO;
    private AvisResponseDTO avisResponseDTO;
    private WishlistResponseDTO wishlistResponseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setNom("John Doe");

        avisResponseDTO = new AvisResponseDTO();
        avisResponseDTO.setId(1L);

        wishlistResponseDTO = new WishlistResponseDTO();
        wishlistResponseDTO.setId(1L);
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        when(userService.getAllUsers()).thenReturn(Arrays.asList(userResponseDTO));

        // Act
        List<UserResponseDTO> result = adminUserController.getAllUsers();

        // Assert
        assertEquals(1, result.size());
        assertEquals(userResponseDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testGetUserById() {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(userResponseDTO);

        // Act
        UserResponseDTO result = adminUserController.getUserById(1L);

        // Assert
        assertEquals(userResponseDTO.getId(), result.getId());
    }

    @Test
    public void testGetUserByEmail() {
        // Arrange
        String email = "john.doe@example.com";
        when(userService.getUserByEmail(email)).thenReturn(userResponseDTO);

        // Act
        UserResponseDTO result = adminUserController.getUserByEmail(email);

        // Assert
        assertEquals(userResponseDTO.getId(), result.getId());
    }

    @Test
    public void testGetUserByUsername() {
        // Arrange
        String username = "JohnDoe";
        when(userService.getUserByName(username)).thenReturn(userResponseDTO);

        // Act
        UserResponseDTO result = adminUserController.getUserByUsername(username);

        // Assert
        assertEquals(userResponseDTO.getId(), result.getId());
    }

    @Test
    public void testAddUser() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setNom("John Doe");

        // Act
        adminUserController.addUser(userDTO);

        // Assert
        verify(userService, times(1)).addUser(userDTO);
    }

    @Test
    public void testAddUserAlreadyExists() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setNom("John Doe");

        doThrow(new EntityAlreadyExistException("User already exists")).when(userService).addUser(any());

        // Act and Assert
        Exception exception = assertThrows(EntityAlreadyExistException.class, () -> {
            adminUserController.addUser(userDTO);
        });

        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    public void testDeleteUser() {
        // Act
        adminUserController.deleteUser(1L);

        // Assert
        verify(userService).deleteUser(1L);
    }

    @Test
    public void testGetAllAvis() {
        // Arrange
        when(avisService.getAllAvis()).thenReturn(Arrays.asList(avisResponseDTO));

        // Act
        List<AvisResponseDTO> result = adminUserController.getAllAvis();

        // Assert
        assertEquals(1, result.size());
        assertEquals(avisResponseDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testGetAvisById() {
        // Arrange
        when(avisService.getAvisById(1L)).thenReturn(avisResponseDTO);

        // Act
        AvisResponseDTO result = adminUserController.getAvisById(1L);

        // Assert
        assertEquals(avisResponseDTO.getId(), result.getId());
    }

    @Test
    public void testDeleteAvis() {
        // Act
        adminUserController.deleteAvis(1L);

        // Assert
        verify(avisService).deleteAvis(1L);
    }

    @Test
    public void testGetAllWishlists() {
        // Arrange
        when(wishListService.getAllWishlists()).thenReturn(Arrays.asList(wishlistResponseDTO));

        // Act
        List<WishlistResponseDTO> result = adminUserController.getAllWishlists();

        // Assert
        assertEquals(1, result.size());
        assertEquals(wishlistResponseDTO.getId(), result.get(0).getId());
    }
}
