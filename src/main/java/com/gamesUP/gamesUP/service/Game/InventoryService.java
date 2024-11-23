package com.gamesUP.gamesUP.service.Game;

import com.gamesUP.gamesUP.DTO.Game.InventoryDTO;
import com.gamesUP.gamesUP.DTO.Game.InventoryResponseDTO;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Inventory;
import com.gamesUP.gamesUP.repository.Game.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryResponseDTO> getAllInventories() {
        return inventoryRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public InventoryResponseDTO getInventoryById(Long id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        return inventory.map(this::convertToDTO).orElse(null);
    }

    public void addInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        inventory.setQuantity(inventoryDTO.getQuantity());
        inventoryRepository.save(inventory);
    }

    public void updateInventory(Long id, InventoryDTO inventoryDTO) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);
        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            inventory.setQuantity(inventoryDTO.getQuantity());
            inventoryRepository.save(inventory);
        }
    }

    public void deleteInventory(Long id) {
        try {
            inventoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new RelationConstraintException("Cannot delete inventory, related entities exist.");
        }
    }

    private InventoryResponseDTO convertToDTO(Inventory inventory) {
        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setId(inventory.getId());
        dto.setQuantity(inventory.getQuantity());
        return dto;
    }
}
