package com.gamesUP.gamesUP.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime date;
	private Boolean paid;
	private Boolean delivered;
	private Boolean archived;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "purchase")
	private List<PurchaseLine> lines;
}
