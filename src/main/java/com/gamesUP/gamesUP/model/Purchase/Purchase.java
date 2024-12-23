package com.gamesUP.gamesUP.model.Purchase;

import com.gamesUP.gamesUP.model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate date;
	private Boolean paid;
	private Boolean delivered;
	private Boolean archived;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

/*	@OneToMany(mappedBy = "purchase")
	private List<PurchaseLine> lines;*/
}
