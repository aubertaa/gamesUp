package com.gamesUP.gamesUP.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResponseTests {

    @Test
    public void testErrorResponseConstructor() {
        // Arrange
        int expectedStatusCode = 404;
        String expectedMessage = "Not Found";

        // Act
        ErrorResponse errorResponse = new ErrorResponse(expectedStatusCode, expectedMessage);

        // Assert
        assertEquals(expectedStatusCode, errorResponse.getStatusCode());
        assertEquals(expectedMessage, errorResponse.getMessage());
    }

    @Test
    public void testGetStatusCode() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse(400, "Bad Request");

        // Act
        int statusCode = errorResponse.getStatusCode();

        // Assert
        assertEquals(400, statusCode);
    }

    @Test
    public void testSetStatusCode() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse(400, "Bad Request");

        // Act
        errorResponse.setStatusCode(500);

        // Assert
        assertEquals(500, errorResponse.getStatusCode());
    }

    @Test
    public void testGetMessage() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse(400, "Bad Request");

        // Act
        String message = errorResponse.getMessage();

        // Assert
        assertEquals("Bad Request", message);
    }

    @Test
    public void testSetMessage() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse(400, "Bad Request");

        // Act
        errorResponse.setMessage("Internal Server Error");

        // Assert
        assertEquals("Internal Server Error", errorResponse.getMessage());
    }
}
