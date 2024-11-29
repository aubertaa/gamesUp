package com.gamesUP.gamesUP.Init;

import com.gamesUP.gamesUP.model.Game.Author;
import com.gamesUP.gamesUP.model.Game.Category;
import com.gamesUP.gamesUP.model.Game.Game;
import com.gamesUP.gamesUP.model.Game.Publisher;
import com.gamesUP.gamesUP.model.Game.Inventory;
import com.gamesUP.gamesUP.model.Purchase.Purchase;
import com.gamesUP.gamesUP.model.Purchase.PurchaseLine;
import com.gamesUP.gamesUP.model.User.Avis;
import com.gamesUP.gamesUP.model.User.User;
import com.gamesUP.gamesUP.model.User.Wishlist;
import com.gamesUP.gamesUP.repository.Game.*;
import com.gamesUP.gamesUP.repository.Purchase.PurchaseLineRepository;
import com.gamesUP.gamesUP.repository.Purchase.PurchaseRepository;
import com.gamesUP.gamesUP.repository.User.AvisRepository;
import com.gamesUP.gamesUP.repository.User.UserRepository;
import com.gamesUP.gamesUP.repository.User.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AvisRepository avisRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseLineRepository purchaseLineRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // --- GAMES ---
        Author author1 = new Author(null, "John Smith");
        Author author2 = new Author(null, "Jane Doe");
        authorRepository.save(author1);
        authorRepository.save(author2);

        Category category1 = new Category(null, "Action");
        Category category2 = new Category(null, "Adventure");
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        Publisher publisher1 = new Publisher(null, "Epic Games");
        Publisher publisher2 = new Publisher(null, "Valve");
        publisherRepository.save(publisher1);
        publisherRepository.save(publisher2);

        Game game1 = new Game(
                null,
                "Fortnite",
                "Battle Royale",
                "3.1",
                9.99f,
                author1,
                category1,
                publisher1
        );

        Game game2 = new Game(
                null,
                "Portal",
                "Puzzle",
                "1",
                4.99f,
                author2,
                category2,
                publisher2
        );

        gameRepository.save(game1);
        gameRepository.save(game2);

        Inventory inventory1 = new Inventory(
                null,
                game1,
                100
        );

        Inventory inventory2 = new Inventory(
                null,
                game2,
                50
        );

        inventoryRepository.save(inventory1);
        inventoryRepository.save(inventory2);


        // --- USERS ---

        User user1 = new User(
                null,
                "Correcteur Visiplus (client)",
                passwordEncoder.encode("123456"),
                "correcteur@example.com",
                Set.of("client")
        );

        User user2 = new User(
                null,
                "Le grand chef (admin)",
                passwordEncoder.encode("123456"),
                "grandchef@example.com",
                Set.of("administrateur")
        );

        userRepository.save(user1);
        userRepository.save(user2);

        // --- PURCHASES ---
        Purchase purchase1 = new Purchase(
                null,
                LocalDate.now().minusDays(10),
                true,
                true,
                false,
                user1
        );

        Purchase purchase2 = new Purchase(
                null,
                LocalDate.now().minusDays(8),
                false,
                false,
                false,
                user2
        );

        Purchase purchase3 = new Purchase(
                null,
                LocalDate.now().minusDays(5),
                false,
                false,
                false,
                user1
        );

        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);
        purchaseRepository.save(purchase3);

        PurchaseLine purchaseLine1 = new PurchaseLine(
                null,
                purchase1,
                game1
        );

        PurchaseLine purchaseLine2 = new PurchaseLine(
                null,
                purchase1,
                game2
        );

        PurchaseLine purchaseLine3 = new PurchaseLine(
                null,
                purchase2,
                game1
        );

        purchaseLineRepository.save(purchaseLine1);
        purchaseLineRepository.save(purchaseLine2);
        purchaseLineRepository.save(purchaseLine3);



        // --- WISHLIST ---
        Wishlist wishlist1 = new Wishlist(
                null,
                user1,
                game1
        );

        Wishlist wishlist2 = new Wishlist(
                null,
                user2,
                game2
        );

        wishlistRepository.save(wishlist1);
        wishlistRepository.save(wishlist2);

        // --- AVIS ---
        Avis avis1 = new Avis(
                null,
                user1,
                game1,
                "Great game!",
                5f
        );

        Avis avis2 = new Avis(
                null,
                user2,
                game1,
                "I love it!",
                4f
        );

        Avis avis3 = new Avis(
                null,
                user1,
                game2,
                "Not bad",
                3f
        );

        avisRepository.save(avis1);
        avisRepository.save(avis2);
        avisRepository.save(avis3);

    }
}