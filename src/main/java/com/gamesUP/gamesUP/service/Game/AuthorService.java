package com.gamesUP.gamesUP.service.Game;

import com.gamesUP.gamesUP.DTO.Game.AuthorDTO;
import com.gamesUP.gamesUP.DTO.Game.AuthorResponseDTO;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Author;
import com.gamesUP.gamesUP.repository.Game.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<AuthorResponseDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AuthorResponseDTO getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(this::convertToDTO).orElse(null);
    }

    public void addAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        authorRepository.save(author);
    }

    public void updateAuthor(Long id, AuthorDTO authorDTO) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            author.setName(authorDTO.getName());
            authorRepository.save(author);
        }
    }

    public void deleteAuthor(Long id) {
        try {
            authorRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new RelationConstraintException("Cannot delete author, related entities exist.");
        }
    }

    private AuthorResponseDTO convertToDTO(Author author) {
        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        return dto;
    }
}
