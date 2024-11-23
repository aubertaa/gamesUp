package com.gamesUP.gamesUP.controller.Game;

import com.gamesUP.gamesUP.DTO.Game.AuthorDTO;
import com.gamesUP.gamesUP.DTO.Game.AuthorResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.Game.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<AuthorResponseDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorResponseDTO getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public void addAuthor(@RequestBody AuthorDTO authorDTO) {
        try {
            authorService.addAuthor(authorDTO);
        } catch (Exception e) {
            throw new EntityAlreadyExistException("Author already exists");
        }
    }

    @PutMapping("/{id}")
    public void updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        authorService.updateAuthor(id, authorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
