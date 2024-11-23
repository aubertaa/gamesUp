package com.gamesUP.gamesUP.DTO.Purchase;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PurchaseDTO {
    private LocalDate date;
    private Boolean paid;
    private Boolean delivered;
    private Boolean archived;
    private Long userId;
}

