package com.gamesUP.gamesUP.service.User;

import com.gamesUP.gamesUP.DTO.User.UserDTO;
import com.gamesUP.gamesUP.DTO.User.UserResponseDTO;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.User.User;
import com.gamesUP.gamesUP.repository.User.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setNom("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("hashedPassword");
        user.setRoles(Set.of("ROLE_USER"));

        userDTO = new UserDTO();
        userDTO.setNom("John Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("plainPassword");
        userDTO.setRole("ROLE_USER");
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        // Act
        List<UserResponseDTO> result = userService.getAllUsers();

        // Assert
        assertEquals(1, result.size());
        assertEquals(user.getId(), result.get(0).getId());
    }

    @Test
    public void testGetUserById() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserResponseDTO result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testGetUserByIdNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        UserResponseDTO result = userService.getUserById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetUserByName() {
        // Arrange
        when(userRepository.findByName("John Doe")).thenReturn(Optional.of(user));

        // Act
        UserResponseDTO result = userService.getUserByName("John Doe");

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testGetUserByNameNotFound() {
        // Arrange
        when(userRepository.findByName("Unknown User")).thenReturn(Optional.empty());

        // Act
        UserResponseDTO result = userService.getUserByName("Unknown User");

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetUserByEmail() {
        // Arrange
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        // Act
        UserResponseDTO result = userService.getUserByEmail("john.doe@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testGetUserByEmailNotFound() {
        // Arrange
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        // Act
        UserResponseDTO result = userService.getUserByEmail("unknown@example.com");

        // Assert
        assertNull(result);
    }

    @Test
    public void testAddUser() {
        // Arrange
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("hashedPassword");

        // Act
        userService.addUser(userDTO);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("hashedPassword");

        // Act
        userService.updateUser(1L, userDTO);

        // Assert
        verify(userRepository, times(1)).save(user);
        assertEquals("hashedPassword", user.getPassword());
    }

    @Test
    public void testUpdateUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        userService.updateUser(1L, userDTO);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository).deleteById(1L);
    }

    @Test
    public void testDeleteUserDataIntegrityViolation() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doThrow(new DataIntegrityViolationException("")).when(userRepository).deleteById(1L);

        // Act and Assert
        assertThrows(RelationConstraintException.class, () -> userService.deleteUser(1L));
    }
}
