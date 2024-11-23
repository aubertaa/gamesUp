package com.gamesUP.gamesUP.model.Game;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories", uniqueConstraints = {
		@UniqueConstraint(columnNames = "type")
})
@Getter
@Setter
@NoArgsConstructor
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Le type est obligatoire")
	private String type;

	@OneToMany(mappedBy = "category")
	private List<Game> games;
}