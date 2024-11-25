package com.gamesUP.gamesUP.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityAlreadyExistExceptionTests {

    @Test
    public void testEntityAlreadyExistExceptionWithMessage() {
        // Arrange
        String message = "Entity already exists";

        // Act
        EntityAlreadyExistException exception = new EntityAlreadyExistException(message);

        // Assert
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testEntityAlreadyExistExceptionWithoutMessage() {
        // Act
        EntityAlreadyExistException exception = new EntityAlreadyExistException();

        // Assert
        assertEquals(null, exception.getMessage());
    }
}
