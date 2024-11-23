package com.gamesUP.gamesUP.DTO.Purchase;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PurchaseResponseDTO {
    private Long id;
    private LocalDateTime date;
    private Boolean paid;
    private Boolean delivered;
    private Boolean archived;
    private Long userId;
}
