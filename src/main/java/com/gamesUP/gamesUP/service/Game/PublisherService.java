package com.gamesUP.gamesUP.service.Game;

import com.gamesUP.gamesUP.DTO.Game.PublisherDTO;
import com.gamesUP.gamesUP.DTO.Game.PublisherResponseDTO;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Publisher;
import com.gamesUP.gamesUP.repository.Game.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;

    public List<PublisherResponseDTO> getAllPublishers() {
        return publisherRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PublisherResponseDTO getPublisherById(Long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        return publisher.map(this::convertToDTO).orElse(null);
    }

    public void addPublisher(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherDTO.getName());
        publisherRepository.save(publisher);
    }

    public void updatePublisher(Long id, PublisherDTO publisherDTO) {
        Optional<Publisher> publisherOptional = publisherRepository.findById(id);
        if (publisherOptional.isPresent()) {
            Publisher publisher = publisherOptional.get();
            publisher.setName(publisherDTO.getName());
            publisherRepository.save(publisher);
        }
    }

    public void deletePublisher(Long id) {
        try {
            publisherRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new RelationConstraintException("Cannot delete publisher, related entities exist.");
        }
    }

    private PublisherResponseDTO convertToDTO(Publisher publisher) {
        PublisherResponseDTO dto = new PublisherResponseDTO();
        dto.setId(publisher.getId());
        dto.setName(publisher.getName());
        return dto;
    }
}
