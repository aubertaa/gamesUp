package com.gamesUP.gamesUP.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "publishers")
@Getter
@Setter
@NoArgsConstructor
public class Publisher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
}
