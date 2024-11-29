package com.gamesUP.gamesUP.service.User;

import com.gamesUP.gamesUP.DTO.User.RecommendationRequest;
import com.gamesUP.gamesUP.model.Game.Game;
import com.gamesUP.gamesUP.model.Purchase.Purchase;
import com.gamesUP.gamesUP.model.Purchase.PurchaseLine;
import com.gamesUP.gamesUP.repository.Game.GameRepository;
import com.gamesUP.gamesUP.repository.Purchase.PurchaseLineRepository;
import com.gamesUP.gamesUP.repository.Purchase.PurchaseRepository;
import com.gamesUP.gamesUP.repository.User.AvisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {


    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);


    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseLineRepository purchaseLineRepository;

    @Autowired
    private AvisRepository avisRepository;

    @Autowired
    private GameRepository gameRepository;

    private final RestTemplate restTemplate;

    public RecommendationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRecommendation(Long userId) {

        // Fetch purchases for the given user from the database
        List<Purchase> purchases = purchaseRepository.findByUserId(userId);

        // Get game data for each purchase
        List<RecommendationRequest.Purchase> purchaseList = purchases.stream()
                .flatMap(purchase -> {
                    // Fetch PurchaseLine entries for each purchase to get associated games
                    List<PurchaseLine> purchaseLines = purchaseLineRepository.findByPurchaseId(purchase.getId());
                    return purchaseLines.stream().map(purchaseLine -> {

                        // Get the game for the given game_id
                        Game game = gameRepository.findById(purchaseLine.getGame().getId()).orElseThrow(() -> new RuntimeException("Game not found"));

                        RecommendationRequest.Purchase purchaseObj = new RecommendationRequest.Purchase();
                        purchaseObj.setGame_id(game.getId());

                        //rating is fetched from Avisrepository
                        Float rating = avisRepository.findByGameAndUserId(game.getId(), userId);
                        purchaseObj.setRating(rating);

                        return purchaseObj;
                    });
                })
                .collect(Collectors.toList());

        logger.info("Fetched purchaseList: {}", purchaseList);

        // Create the request body for the recommendation API
        RecommendationRequest request = new RecommendationRequest();
        request.setUser_id(userId);
        request.setPurchases(purchaseList);

        // Define the URL for the recommendation API
        String url = "http://python-backend:8000/recommendations/";

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap the body and headers into an HttpEntity
        HttpEntity<RecommendationRequest> entity = new HttpEntity<>(request, headers);

        // Send the POST request to the Python API
        logger.info(" entity: {}", entity);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Return the response body from the Python API
        logger.info(" response: {}", response);
        logger.info(" response.getBody(): {}", response.getBody());
        return response.getBody();
    }
}
