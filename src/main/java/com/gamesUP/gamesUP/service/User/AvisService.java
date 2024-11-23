package com.gamesUP.gamesUP.service.User;

import com.gamesUP.gamesUP.DTO.User.AvisDTO;
import com.gamesUP.gamesUP.DTO.User.AvisResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityDontExistException;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Game;
import com.gamesUP.gamesUP.model.User.Avis;
import com.gamesUP.gamesUP.model.User.User;
import com.gamesUP.gamesUP.repository.User.AvisRepository;
import com.gamesUP.gamesUP.repository.User.UserRepository;
import com.gamesUP.gamesUP.repository.Game.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvisService {
    @Autowired
    private AvisRepository avisRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    public List<AvisResponseDTO> getAllAvis() {
        return avisRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AvisResponseDTO getAvisById(Long id) {
        Optional<Avis> avis = avisRepository.findById(id);
        return avis.map(this::convertToDTO).orElse(null);
    }

    public void addAvis(AvisDTO avisDTO) {

        Avis avis = new Avis();

        // Check if User exists
        User user = userRepository.findById(avisDTO.getUserId())
                .orElseThrow(() -> new EntityDontExistException("User not found: " + avisDTO.getUserId()));
        avis.setUser(user);

        // Check if Game exists
        Game game = gameRepository.findById(avisDTO.getGameId())
                .orElseThrow(() -> new EntityDontExistException("Game not found: " + avisDTO.getGameId()));
        avis.setGame(game);

        avis.setCommentaire(avisDTO.getCommentaire());
        avis.setNote(avisDTO.getNote());
        avisRepository.save(avis);
    }

    public void updateAvis(Long id, AvisDTO avisDTO) {
        Optional<Avis> avisOptional = avisRepository.findById(id);
        if (avisOptional.isPresent()) {
            Avis avis = avisOptional.get();
            // Check if User exists
            User user = userRepository.findById(avisDTO.getUserId())
                    .orElseThrow(() -> new EntityDontExistException("User not found: " + avisDTO.getUserId()));
            avis.setUser(user);

            // Check if Game exists
            Game game = gameRepository.findById(avisDTO.getGameId())
                    .orElseThrow(() -> new EntityDontExistException("Game not found: " + avisDTO.getGameId()));
            avis.setGame(game);

            avis.setCommentaire(avisDTO.getCommentaire());
            avis.setNote(avisDTO.getNote());
            avisRepository.save(avis);
        }
    }

    public void deleteAvis(Long id) {
        try {
            avisRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new RelationConstraintException("Cannot delete avis, related entities exist.");
        }
    }

    private AvisResponseDTO convertToDTO(Avis avis) {
        AvisResponseDTO dto = new AvisResponseDTO();
        dto.setId(avis.getId());
        dto.setUserId(avis.getUser().getId());
        dto.setGameId(avis.getGame().getId());
        dto.setCommentaire(avis.getCommentaire());
        dto.setNote(avis.getNote());
        return dto;
    }
}