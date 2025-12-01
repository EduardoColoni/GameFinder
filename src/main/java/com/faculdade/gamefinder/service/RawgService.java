package com.faculdade.gamefinder.service;

import com.faculdade.gamefinder.model.GameResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class RawgService {

    private final WebClient webClient;

    @Value("${rawg.api.key}")
    private String apiKey;

    // Injeção via construtor (recomendado)
    public RawgService(WebClient.Builder webClientBuilder, @Value("${rawg.api.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public GameResponse.Game buscarJogo(String nome) {
        try {
            GameResponse response = this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("games")
                            .queryParam("key", apiKey)
                            .queryParam("search", nome)
                            .build())
                    .retrieve()
                    .bodyToMono(GameResponse.class)
                    .block(); // Bloqueia a chamada para funcionar como MVC tradicional

            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                // Retorna o primeiro da lista
                return response.getResults().get(0);
            }
            return null;

        } catch (WebClientResponseException e) {
            System.err.println("Erro HTTP: " + e.getStatusCode());
            return null;
        } catch (Exception e) {
            System.err.println("Erro genérico: " + e.getMessage());
            return null;
        }
    }
}