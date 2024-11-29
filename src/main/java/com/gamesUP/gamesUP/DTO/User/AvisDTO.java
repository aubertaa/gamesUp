package com.gamesUP.gamesUP.DTO.User;

import lombok.Data;

@Data
public class AvisDTO {
    private Long userId;
    private Long gameId;
    private String commentaire;
    private Float note;
}

