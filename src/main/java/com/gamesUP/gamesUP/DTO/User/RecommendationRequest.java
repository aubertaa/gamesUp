package com.gamesUP.gamesUP.DTO.User;

import java.util.List;
import lombok.Data;

@Data
public class RecommendationRequest {

    private Long user_id;
    private List<Purchase> purchases;

    @Data
    public static class Purchase {
        private Long game_id;
        private Float rating;

    }
}
