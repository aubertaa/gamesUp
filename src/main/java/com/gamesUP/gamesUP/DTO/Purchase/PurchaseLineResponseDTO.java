package com.gamesUP.gamesUP.DTO.Purchase;

import lombok.Data;

@Data
public class PurchaseLineResponseDTO {
    private Long id;
    private Long purchaseId;
    private Long gameId;
}
