package com.gamesUP.gamesUP.controller.Game;

import com.gamesUP.gamesUP.DTO.Game.InventoryDTO;
import com.gamesUP.gamesUP.DTO.Game.InventoryResponseDTO;
import com.gamesUP.gamesUP.service.Game.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<InventoryResponseDTO> getAllInventories() {
        return inventoryService.getAllInventories();
    }

    @GetMapping("/{id}")
    public InventoryResponseDTO getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    @PostMapping
    public void addInventory(@RequestBody InventoryDTO inventoryDTO) {
            inventoryService.addInventory(inventoryDTO);
    }

    @PutMapping("/{id}")
    public void updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {
        inventoryService.updateInventory(id, inventoryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
    }
}
