package com.gamesUP.gamesUP.Exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GlobalExceptionHandlerTests {

    private GlobalExceptionHandler globalExceptionHandler;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testHandleEntityDontExistException() {
        // Arrange
        EntityDontExistException exception = new EntityDontExistException("Entity not found");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatusCode());
        assertEquals("Entity not found", response.getBody().getMessage());
    }

    @Test
    public void testHandleEntityAlreadyExistException() {
        // Arrange
        EntityAlreadyExistException exception = new EntityAlreadyExistException("Entity already exists");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleEntityAlreadyExistException(exception);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatusCode());
        assertEquals("Entity already exists", response.getBody().getMessage());
    }

    @Test
    public void testHandleRelationConstraintException() {
        // Arrange
        RelationConstraintException exception = new RelationConstraintException("Relation constraint error");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRelationConstraintException(exception);

        // Assert
        assertEquals(HttpStatus.FAILED_DEPENDENCY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.FAILED_DEPENDENCY.value(), response.getBody().getStatusCode());
        assertEquals("Relation constraint error", response.getBody().getMessage());
    }
}
