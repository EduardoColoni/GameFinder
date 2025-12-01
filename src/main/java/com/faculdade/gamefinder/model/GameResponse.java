package com.faculdade.gamefinder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class GameResponse {
    private List<Game> results;

    @Data
    public static class Game {
        private Long id;
        private String name;
        private String released;
        @JsonProperty("background_image")
        private String backgroundImage;
        private Double rating;
    }
}