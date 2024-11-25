package com.gamesUP.gamesUP.service.Game;

import com.gamesUP.gamesUP.DTO.Game.AuthorDTO;
import com.gamesUP.gamesUP.DTO.Game.AuthorResponseDTO;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Author;
import com.gamesUP.gamesUP.repository.Game.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorServiceTests {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author1;
    private Author author2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize some Author objects
        author1 = new Author();
        author1.setId(1L);
        author1.setName("Author One");

        author2 = new Author();
        author2.setId(2L);
        author2.setName("Author Two");
    }

    @Test
    public void testGetAllAuthors() {
        // Arrange
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        // Act
        List<AuthorResponseDTO> result = authorService.getAllAuthors();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Author One", result.get(0).getName());
        assertEquals("Author Two", result.get(1).getName());
    }

    @Test
    public void testGetAuthorByIdFound() {
        // Arrange
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author1));

        // Act
        AuthorResponseDTO result = authorService.getAuthorById(authorId);

        // Assert
        assertNotNull(result);
        assertEquals(author1.getName(), result.getName());
    }

    @Test
    public void testGetAuthorByIdNotFound() {
        // Arrange
        Long authorId = 999L; // Non-existent ID
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act
        AuthorResponseDTO result = authorService.getAuthorById(authorId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testAddAuthor() {
        // Arrange
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("New Author");

        // Act
        authorService.addAuthor(authorDTO);

        // Assert
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    public void testUpdateAuthorFound() {
        // Arrange
        Long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Updated Author");
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author1));

        // Act
        authorService.updateAuthor(authorId, authorDTO);

        // Assert
        verify(authorRepository, times(1)).save(author1);
        assertEquals("Updated Author", author1.getName());
    }

    @Test
    public void testUpdateAuthorNotFound() {
        // Arrange
        Long authorId = 999L; // Non-existent ID
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Updated Author");
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act
        authorService.updateAuthor(authorId, authorDTO);

        // Assert
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    public void testDeleteAuthor() {
        // Arrange
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author1));

        // Act
        authorService.deleteAuthor(authorId);

        // Assert
        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    public void testDeleteAuthorWithRelationConstraint() {
        // Arrange
        Long authorId = 1L;
        doThrow(new DataIntegrityViolationException("")).when(authorRepository).deleteById(authorId);

        // Act and Assert
        Exception exception = assertThrows(RelationConstraintException.class, () -> {
            authorService.deleteAuthor(authorId);
        });

        assertEquals("Cannot delete author, related entities exist.", exception.getMessage());
    }
}
