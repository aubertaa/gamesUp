package com.gamesUP.gamesUP.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RelationConstraintExceptionTests {

    @Test
    public void testRelationConstraintExceptionMessage() {
        // Arrange
        String expectedMessage = "This is a test message";

        // Act
        RelationConstraintException exception = new RelationConstraintException(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testRelationConstraintExceptionConstructor() {
        // Arrange
        String expectedMessage = "Error occurred due to relation constraint";

        // Act
        RelationConstraintException exception = new RelationConstraintException(expectedMessage);

        // Assert
        assertEquals("Error occurred due to relation constraint", exception.getMessage());
    }
}
