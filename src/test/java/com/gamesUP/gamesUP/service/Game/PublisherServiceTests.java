package com.gamesUP.gamesUP.service.Game;

import com.gamesUP.gamesUP.DTO.Game.PublisherDTO;
import com.gamesUP.gamesUP.DTO.Game.PublisherResponseDTO;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Publisher;
import com.gamesUP.gamesUP.repository.Game.PublisherRepository;
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

public class PublisherServiceTests {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private Publisher publisher1;
    private Publisher publisher2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        publisher1 = new Publisher();
        publisher1.setId(1L);
        publisher1.setName("Publisher One");

        publisher2 = new Publisher();
        publisher2.setId(2L);
        publisher2.setName("Publisher Two");
    }

    @Test
    public void testGetAllPublishers() {
        // Arrange
        when(publisherRepository.findAll()).thenReturn(Arrays.asList(publisher1, publisher2));

        // Act
        List<PublisherResponseDTO> result = publisherService.getAllPublishers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Publisher One", result.get(0).getName());
        assertEquals("Publisher Two", result.get(1).getName());
    }

    @Test
    public void testGetPublisherByIdFound() {
        // Arrange
        Long publisherId = 1L;
        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(publisher1));

        // Act
        PublisherResponseDTO result = publisherService.getPublisherById(publisherId);

        // Assert
        assertNotNull(result);
        assertEquals("Publisher One", result.getName());
    }

    @Test
    public void testGetPublisherByIdNotFound() {
        // Arrange
        Long publisherId = 999L; // Non-existent ID
        when(publisherRepository.findById(publisherId)).thenReturn(Optional.empty());

        // Act
        PublisherResponseDTO result = publisherService.getPublisherById(publisherId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testAddPublisher() {
        // Arrange
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setName("New Publisher");

        // Act
        publisherService.addPublisher(publisherDTO);

        // Assert
        verify(publisherRepository, times(1)).save(any(Publisher.class));
    }

    @Test
    public void testUpdatePublisherFound() {
        // Arrange
        Long publisherId = 1L;
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setName("Updated Publisher");
        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(publisher1));

        // Act
        publisherService.updatePublisher(publisherId, publisherDTO);

        // Assert
        assertEquals("Updated Publisher", publisher1.getName());
        verify(publisherRepository, times(1)).save(publisher1);
    }

    @Test
    public void testUpdatePublisherNotFound() {
        // Arrange
        Long publisherId = 999L; // Non-existent ID
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setName("Updated Publisher");
        when(publisherRepository.findById(publisherId)).thenReturn(Optional.empty());

        // Act
        publisherService.updatePublisher(publisherId, publisherDTO);

        // Assert
        verify(publisherRepository, never()).save(any(Publisher.class));
    }

    @Test
    public void testDeletePublisher() {
        // Arrange
        Long publisherId = 1L;
        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(publisher1));

        // Act
        publisherService.deletePublisher(publisherId);

        // Assert
        verify(publisherRepository, times(1)).deleteById(publisherId);
    }

    @Test
    public void testDeletePublisherWithRelationConstraint() {
        // Arrange
        Long publisherId = 1L;
        doThrow(new DataIntegrityViolationException("")).when(publisherRepository).deleteById(publisherId);

        // Act and Assert
        Exception exception = assertThrows(RelationConstraintException.class, () -> {
            publisherService.deletePublisher(publisherId);
        });

        assertEquals("Cannot delete publisher, related entities exist.", exception.getMessage());
    }
}
