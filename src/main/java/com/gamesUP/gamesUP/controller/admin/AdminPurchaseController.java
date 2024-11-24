package com.gamesUP.gamesUP.controller.admin;

import com.gamesUP.gamesUP.DTO.Purchase.PurchaseLineResponseDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseResponseDTO;
import com.gamesUP.gamesUP.service.Purchase.PurchaseLineService;
import com.gamesUP.gamesUP.service.Purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/purchases")
public class AdminPurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseLineService purchaseLineService;

    @GetMapping
    public List<PurchaseResponseDTO> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    
    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
    }
    
    @GetMapping("/lines")
    public List<PurchaseLineResponseDTO> getAllPurchaseLines() {
        return purchaseLineService.getAllPurchaseLines();
    }

}
