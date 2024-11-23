package com.gamesUP.gamesUP.controller.Game;

import com.gamesUP.gamesUP.DTO.Game.PublisherDTO;
import com.gamesUP.gamesUP.DTO.Game.PublisherResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.Game.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @GetMapping
    public List<PublisherResponseDTO> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("/{id}")
    public PublisherResponseDTO getPublisherById(@PathVariable Long id) {
        return publisherService.getPublisherById(id);
    }

    @PostMapping
    public void addPublisher(@RequestBody PublisherDTO publisherDTO) {
        try {
            publisherService.addPublisher(publisherDTO);
        } catch (Exception e) {
            throw new EntityAlreadyExistException("Publisher already exists");
        }
    }

    @PutMapping("/{id}")
    public void updatePublisher(@PathVariable Long id, @RequestBody PublisherDTO publisherDTO) {
        publisherService.updatePublisher(id, publisherDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
    }
}
