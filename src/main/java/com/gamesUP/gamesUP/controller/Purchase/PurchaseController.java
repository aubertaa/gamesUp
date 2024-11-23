package com.gamesUP.gamesUP.controller.Purchase;

import com.gamesUP.gamesUP.DTO.Purchase.PurchaseDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseResponseDTO;
import com.gamesUP.gamesUP.service.Purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping
    public List<PurchaseResponseDTO> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    @GetMapping("/{id}")
    public PurchaseResponseDTO getPurchaseById(@PathVariable Long id) {
        return purchaseService.getPurchaseById(id);
    }

    @PostMapping
    public void addPurchase(@RequestBody PurchaseDTO purchaseDTO) {
            purchaseService.addPurchase(purchaseDTO);
    }

    @PutMapping("/{id}")
    public void updatePurchase(@PathVariable Long id, @RequestBody PurchaseDTO purchaseDTO) {
        purchaseService.updatePurchase(id, purchaseDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
    }
}
