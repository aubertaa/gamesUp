package com.gamesUP.gamesUP.service.Purchase;

import com.gamesUP.gamesUP.DTO.Purchase.PurchaseLineDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseLineResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityDontExistException;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Game;
import com.gamesUP.gamesUP.model.Purchase.Purchase;
import com.gamesUP.gamesUP.model.Purchase.PurchaseLine;
import com.gamesUP.gamesUP.repository.Game.GameRepository;
import com.gamesUP.gamesUP.repository.Purchase.PurchaseLineRepository;
import com.gamesUP.gamesUP.repository.Purchase.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseLineService {
    @Autowired
    private PurchaseLineRepository purchaseLineRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private GameRepository gameRepository;

    public List<PurchaseLineResponseDTO> getAllPurchaseLines() {
        return purchaseLineRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PurchaseLineResponseDTO getPurchaseLineById(Long id) {
        Optional<PurchaseLine> purchaseLine = purchaseLineRepository.findById(id);
        return purchaseLine.map(this::convertToDTO).orElse(null);
    }

    public void addPurchaseLine(PurchaseLineDTO purchaseLineDTO) {
        PurchaseLine purchaseLine = new PurchaseLine();

        // Check if Purchase exists
        Purchase purchase = purchaseRepository.findById(purchaseLineDTO.getPurchaseId())
                .orElseThrow(() -> new EntityDontExistException("Purchase not found: " + purchaseLineDTO.getPurchaseId()));
        purchaseLine.setPurchase(purchase);

        // Check if Game exists
        Game game = gameRepository.findById(purchaseLineDTO.getGameId())
                .orElseThrow(() -> new EntityDontExistException("Game not found: " + purchaseLineDTO.getGameId()));
        purchaseLine.setGame(game);

        purchaseLineRepository.save(purchaseLine);
    }

    public void updatePurchaseLine(Long id, PurchaseLineDTO purchaseLineDTO) {
        Optional<PurchaseLine> purchaseLineOptional = purchaseLineRepository.findById(id);
        if (purchaseLineOptional.isPresent()) {
            PurchaseLine purchaseLine = purchaseLineOptional.get();

            // Check if Purchase exists
            Purchase purchase = purchaseRepository.findById(purchaseLineDTO.getPurchaseId())
                    .orElseThrow(() -> new EntityDontExistException("Purchase not found: " + purchaseLineDTO.getPurchaseId()));
            purchaseLine.setPurchase(purchase);

            // Check if Game exists
            Game game = gameRepository.findById(purchaseLineDTO.getGameId())
                    .orElseThrow(() -> new EntityDontExistException("Game not found: " + purchaseLineDTO.getGameId()));
            purchaseLine.setGame(game);

            purchaseLineRepository.save(purchaseLine);
        }
    }

    public void deletePurchaseLine(Long id) {
        try {
            purchaseLineRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new RelationConstraintException("Cannot delete purchaseLine, related entities exist.");
        }
    }

    private PurchaseLineResponseDTO convertToDTO(PurchaseLine purchaseLine) {
        PurchaseLineResponseDTO dto = new PurchaseLineResponseDTO();
        dto.setId(purchaseLine.getId());
        dto.setPurchaseId(purchaseLine.getPurchase().getId());
        dto.setGameId(purchaseLine.getGame().getId());
        return dto;
    }
}
