package com.faculdade.gamefinder.service;

import com.faculdade.gamefinder.model.GameDetails;
import com.faculdade.gamefinder.model.GameResponse;
import com.faculdade.gamefinder.model.GameResponse.Game;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class RawgService {

    private final WebClient webClient;

    @Value("${rawg.api.key}")
    private String apiKey;

    public RawgService(WebClient.Builder webClientBuilder, @Value("${rawg.api.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Game buscarJogo(String nome) {
        // 1. PRIMEIRA CHAMADA: BUSCAR ID DO JOGO
        Game game = buscarIdPeloNome(nome);

        if (game == null) {
            return null; // Jogo não encontrado na busca inicial
        }

        // 2. SEGUNDA CHAMADA: BUSCAR DETALHES PELO ID
        GameDetails details = buscarDetalhesPeloId(game.getId());

        if (details != null) {
            // Combina os resultados
            game.setDescription(details.getDescription_raw());
        }

        return game;
    }

    private Game buscarIdPeloNome(String nome) {
        try {
            GameResponse response = this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("games")
                            .queryParam("key", apiKey)
                            .queryParam("search", nome)
                            .build())
                    .retrieve()
                    .bodyToMono(GameResponse.class)
                    .block();

            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                return response.getResults().get(0); // Pega o primeiro resultado da busca
            }
            return null;

        } catch (WebClientResponseException e) {
            System.err.println("Erro HTTP na busca: " + e.getStatusCode());
            return null;
        }
    }

    private GameDetails buscarDetalhesPeloId(Long id) {
        try {
            // Endpoint: /api/games/{id}
            return this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("games/{id}")
                            .queryParam("key", apiKey)
                            .build(id))
                    .retrieve()
                    .bodyToMono(GameDetails.class)
                    .block();

        } catch (WebClientResponseException e) {
            System.err.println("Erro HTTP nos detalhes (ID: " + id + "): " + e.getStatusCode());
            return null;
        }
    }
}