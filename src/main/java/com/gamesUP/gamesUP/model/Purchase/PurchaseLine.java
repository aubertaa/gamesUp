package com.gamesUP.gamesUP.model.Purchase;

import com.gamesUP.gamesUP.model.Game.Game;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_lines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
