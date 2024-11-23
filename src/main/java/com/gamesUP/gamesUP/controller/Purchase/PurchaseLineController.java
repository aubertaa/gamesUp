package com.gamesUP.gamesUP.controller.Purchase;

import com.gamesUP.gamesUP.DTO.Purchase.PurchaseLineDTO;
import com.gamesUP.gamesUP.DTO.Purchase.PurchaseLineResponseDTO;
import com.gamesUP.gamesUP.service.Purchase.PurchaseLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchaselines")
public class PurchaseLineController {
    @Autowired
    private PurchaseLineService purchaseLineService;

    @GetMapping
    public List<PurchaseLineResponseDTO> getAllPurchaseLines() {
        return purchaseLineService.getAllPurchaseLines();
    }

    @GetMapping("/{id}")
    public PurchaseLineResponseDTO getPurchaseLineById(@PathVariable Long id) {
        return purchaseLineService.getPurchaseLineById(id);
    }

    @PostMapping
    public void addPurchaseLine(@RequestBody PurchaseLineDTO purchaseLineDTO) {
            purchaseLineService.addPurchaseLine(purchaseLineDTO);
    }

    @PutMapping("/{id}")
    public void updatePurchaseLine(@PathVariable Long id, @RequestBody PurchaseLineDTO purchaseLineDTO) {
        purchaseLineService.updatePurchaseLine(id, purchaseLineDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePurchaseLine(@PathVariable Long id) {
        purchaseLineService.deletePurchaseLine(id);
    }
}
