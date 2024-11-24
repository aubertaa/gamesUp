package com.gamesUP.gamesUP.DTO.User;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String nom;
    private String password;
    private String email;
    private String role;
}
