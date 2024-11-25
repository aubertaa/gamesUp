package com.gamesUP.gamesUP.security;

import com.gamesUP.gamesUP.model.User.User;
import com.gamesUP.gamesUP.repository.User.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CustomUserDetailsServiceTests {

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a sample user for testing
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("hashed_password");
        user.setRoles(Set.of("client")); // Assuming "client" role
    }

    @Test
    public void testLoadUserByUsernameSuccess() {
        // Arrange: Mock the user repository to return the sample user
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Act: Call the method under test
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        // Assert: Check the user details returned
        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_client")));
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        // Arrange: Mock the user repository to return an empty optional
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        // Act and Assert: Check that an exception is thrown
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(user.getEmail());
        });
    }
}
