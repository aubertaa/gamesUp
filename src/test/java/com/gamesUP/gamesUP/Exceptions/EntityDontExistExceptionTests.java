package com.gamesUP.gamesUP.Exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityDontExistExceptionTests {

    @Test
    public void testConstructorWithMessage() {
        // Arrange
        String message = "Entity not found";

        // Act
        EntityDontExistException exception = new EntityDontExistException(message);

        // Assert
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testDefaultConstructor() {
        // Act
        EntityDontExistException exception = new EntityDontExistException();

        // Assert
        assertEquals(null, exception.getMessage());
    }

    @Test
    public void testResponseStatus() {
        // Arrange
        EntityDontExistException exception = new EntityDontExistException("Entity not found");

        // Act & Assert
        assertEquals(HttpStatus.NOT_FOUND, org.springframework.http.HttpStatus.NOT_FOUND);
    }
}
