package com.gamesUP.gamesUP.DTO.Purchase;

import lombok.Data;

@Data
public class PurchaseLineDTO {
    private Long purchaseId;
    private Long gameId;
    private Double prix;
}

