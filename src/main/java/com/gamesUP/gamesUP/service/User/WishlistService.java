package com.gamesUP.gamesUP.service.User;

import com.gamesUP.gamesUP.DTO.User.WishlistDTO;
import com.gamesUP.gamesUP.DTO.User.WishlistResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityDontExistException;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Game;
import com.gamesUP.gamesUP.model.User.User;
import com.gamesUP.gamesUP.model.User.Wishlist;
import com.gamesUP.gamesUP.repository.Game.GameRepository;
import com.gamesUP.gamesUP.repository.User.UserRepository;
import com.gamesUP.gamesUP.repository.User.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    public List<WishlistResponseDTO> getAllWishlists() {
        return wishListRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public WishlistResponseDTO getWishlistById(Long id) {
        Optional<Wishlist> wishList = wishListRepository.findById(id);
        return wishList.map(this::convertToDTO).orElse(null);
    }

    public void addWishlist(WishlistDTO wishListDTO) {
        Wishlist wishList = new Wishlist();

        // Check if User exists
        User user = userRepository.findById(wishListDTO.getUserId())
                .orElseThrow(() -> new EntityDontExistException("User not found: " + wishListDTO.getUserId()));
        wishList.setUser(user);

        // Check if Game exists
        Game game = gameRepository.findById(wishListDTO.getGameId())
                .orElseThrow(() -> new EntityDontExistException("Game not found: " + wishListDTO.getGameId()));
        wishList.setGame(game);

        wishListRepository.save(wishList);
    }

    public void updateWishlist(Long id, WishlistDTO wishListDTO) {
        Optional<Wishlist> wishListOptional = wishListRepository.findById(id);
        if (wishListOptional.isPresent()) {
            Wishlist wishList = wishListOptional.get();

            // Check if User exists
            User user = userRepository.findById(wishListDTO.getUserId())
                    .orElseThrow(() -> new EntityDontExistException("User not found: " + wishListDTO.getUserId()));
            wishList.setUser(user);

            // Check if Game exists
            Game game = gameRepository.findById(wishListDTO.getGameId())
                    .orElseThrow(() -> new EntityDontExistException("Game not found: " + wishListDTO.getGameId()));
            wishList.setGame(game);

            wishListRepository.save(wishList);
        }
    }

    public void deleteWishlist(Long id) {
        try {
            wishListRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new RelationConstraintException("Cannot delete wish list, related entities exist.");
        }
    }

    private WishlistResponseDTO convertToDTO(Wishlist wishList) {
        WishlistResponseDTO dto = new WishlistResponseDTO();
        dto.setId(wishList.getId());
        dto.setUserId(wishList.getUser().getId());
        dto.setGameId(wishList.getGame().getId());
        return dto;
    }
}
