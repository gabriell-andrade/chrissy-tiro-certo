package com.chrissytirocerto.client;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FootballApiClient {

    private final String API_KEY = "9a09afc046df704f3b5e153a77f56c9b";

    private final RestTemplate restTemplate = new RestTemplate();

    private HttpEntity<String> createEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-apisports-key", API_KEY);
        return new HttpEntity<>(headers);
    }

    // 🔹 Últimos jogos do time
    public String getLastMatches(int teamId) {

        String url = "https://v3.football.api-sports.io/fixtures?team="
                + teamId + "&season=2024";

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                createEntity(),
                String.class
        );

        return response.getBody();
    }

    // 🔥 Estatísticas do jogo
    public String getMatchStatistics(int fixtureId) {

        String url = "https://v3.football.api-sports.io/fixtures/statistics?fixture="
                + fixtureId;

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                createEntity(),
                String.class
        );

        return response.getBody();
    }

    public String getTeamInfo(int teamId) {
        String url = "https://v3.football.api-sports.io/teams?id=" + teamId;

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                createEntity(),
                String.class
        );

        return response.getBody();
    }

    // 🚀 NOVO: Buscar times por nome (AUTOCOMPLETE)
    public String searchTeams(String name) {

        String url = "https://v3.football.api-sports.io/teams?search=" + name;

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                createEntity(),
                String.class
        );

        return response.getBody();
    }
}