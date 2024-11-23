package com.gamesUP.gamesUP.service.User;

import com.gamesUP.gamesUP.DTO.User.UserDTO;
import com.gamesUP.gamesUP.DTO.User.UserResponseDTO;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.User.User;
import com.gamesUP.gamesUP.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO).orElse(null);
    }

    public UserResponseDTO getUserByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        return user.map(this::convertToDTO).orElse(null);
    }

    public UserResponseDTO getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(this::convertToDTO).orElse(null);
    }

    public void addUser(UserDTO userDTO) {
        User user = new User();
        user.setNom(userDTO.getNom());
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
    }

    public void updateUser(Long id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setNom(userDTO.getNom());
            userRepository.save(user);
        }
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new RelationConstraintException("Cannot delete user, related entities exist.");
        }
    }

    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
