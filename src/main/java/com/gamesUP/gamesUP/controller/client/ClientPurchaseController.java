package com.gamesUP.gamesUP.controller.client;

import com.gamesUP.gamesUP.DTO.Purchase.PurchaseDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseLineDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseLineResponseDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseResponseDTO;
import com.gamesUP.gamesUP.service.Purchase.PurchaseLineService;
import com.gamesUP.gamesUP.service.Purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/purchases")
public class ClientPurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseLineService purchaseLineService;
    
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
    
    @GetMapping("/lines/{id}")
    public PurchaseLineResponseDTO getPurchaseLineById(@PathVariable Long id) {
        return purchaseLineService.getPurchaseLineById(id);
    }

    @PostMapping("/lines")
    public void addPurchaseLine(@RequestBody PurchaseLineDTO purchaseLineDTO) {
        purchaseLineService.addPurchaseLine(purchaseLineDTO);
    }

    @PutMapping("/lines/{id}")
    public void updatePurchaseLine(@PathVariable Long id, @RequestBody PurchaseLineDTO purchaseLineDTO) {
        purchaseLineService.updatePurchaseLine(id, purchaseLineDTO);
    }
    
    @DeleteMapping("/lines/{id}")
    public void deletePurchaseLine(@PathVariable Long id) {
        purchaseLineService.deletePurchaseLine(id);
    }

}
