package com.gamesUP.gamesUP.DTO.User;

import lombok.Data;

@Data
public class AvisResponseDTO {
    private Long id;
    private Long userId;
    private Long gameId;
    private String commentaire;
    private Integer note;
}
