package com.gamesUP.gamesUP.DTO.Game;

import lombok.Data;

@Data
public class GameResponseDTO {
    private Long id;
    private String nom;
    private String genre;
    private String numEdition;
    private String authorName;
    private String categoryType;
    private String publisherName;
}
