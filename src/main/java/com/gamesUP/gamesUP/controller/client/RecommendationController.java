package com.gamesUP.gamesUP.controller.client;

import com.gamesUP.gamesUP.service.User.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public String getRecommendation(@RequestParam Long userId) {
        return recommendationService.getRecommendation(userId);
    }
}
