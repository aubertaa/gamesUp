package com.gamesUP.gamesUP.service.Purchase;

import com.gamesUP.gamesUP.DTO.Purchase.PurchaseDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityDontExistException;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Purchase.Purchase;
import com.gamesUP.gamesUP.model.User.User;
import com.gamesUP.gamesUP.repository.Purchase.PurchaseRepository;
import com.gamesUP.gamesUP.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<PurchaseResponseDTO> getAllPurchases() {
        return purchaseRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PurchaseResponseDTO getPurchaseById(Long id) {
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        return purchase.map(this::convertToDTO).orElse(null);
    }

    public void addPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDTO.getDate());
        purchase.setPaid(purchaseDTO.getPaid());
        purchase.setDelivered(purchaseDTO.getDelivered());
        purchase.setArchived(purchaseDTO.getArchived());

        // Check if User exists
        User user = userRepository.findById(purchaseDTO.getUserId())
                .orElseThrow(() -> new EntityDontExistException("User not found: " + purchaseDTO.getUserId()));
        purchase.setUser(user);

        purchaseRepository.save(purchase);
    }

    public void updatePurchase(Long id, PurchaseDTO purchaseDTO) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);
        if (purchaseOptional.isPresent()) {
            Purchase purchase = purchaseOptional.get();
            purchase.setDate(purchaseDTO.getDate());
            purchase.setPaid(purchaseDTO.getPaid());
            purchase.setDelivered(purchaseDTO.getDelivered());
            purchase.setArchived(purchaseDTO.getArchived());

            // Check if User exists
            User user = userRepository.findById(purchaseDTO.getUserId())
                    .orElseThrow(() -> new EntityDontExistException("User not found: " + purchaseDTO.getUserId()));
            purchase.setUser(user);

            purchaseRepository.save(purchase);
        }
    }

    public void deletePurchase(Long id) {
        try {
            purchaseRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new RelationConstraintException("Cannot delete Purchase, related entities exist.");
        }
    }

    private PurchaseResponseDTO convertToDTO(Purchase purchase) {
        PurchaseResponseDTO dto = new PurchaseResponseDTO();
        dto.setId(purchase.getId());
        dto.setDate(purchase.getDate());
        dto.setPaid(purchase.getPaid());
        dto.setDelivered(purchase.getDelivered());
        dto.setArchived(purchase.getArchived());
        dto.setUserId(purchase.getUser().getId());
        return dto;
    }
}
