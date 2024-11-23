package com.gamesUP.gamesUP.DTO.Game;

import lombok.Data;

@Data
public class InventoryResponseDTO {
    private Long id;
    private Long gameId;
    private Integer quantity;
}
