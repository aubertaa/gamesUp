package com.gamesUP.gamesUP.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_lines")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private Double prix;
}
