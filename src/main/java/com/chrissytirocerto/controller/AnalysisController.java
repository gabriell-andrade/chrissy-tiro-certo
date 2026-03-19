package com.chrissytirocerto.controller;

import com.chrissytirocerto.client.FootballApiClient;
import com.chrissytirocerto.model.MatchAnalysis;
import com.chrissytirocerto.model.MatchStats;
import com.chrissytirocerto.model.TeamDTO;
import com.chrissytirocerto.service.AnalysisService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    private final AnalysisService service;
    private final FootballApiClient client;

    public AnalysisController(AnalysisService service, FootballApiClient client) {
        this.service = service;
        this.client = client;
    }

    // 🔍 ANALISE NORMAL (já existente)
    @GetMapping
    public MatchAnalysis analyzeMatch(
            @RequestParam int teamA,
            @RequestParam int teamB
    ) {

        MatchStats statsA = new MatchStats("Time A", 0, 0, 0);
        MatchStats statsB = new MatchStats("Time B", 0, 0, 0);

        return service.analyze(statsA, statsB, teamA, teamB);
    }

    // 🚀 NOVO: AUTOCOMPLETE DE TIMES
    @GetMapping("/teams")
    public List<TeamDTO> searchTeams(@RequestParam String search) {

        List<TeamDTO> teams = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String response = client.searchTeams(search);
            JsonNode json = mapper.readTree(response).get("response");

            for (JsonNode node : json) {
                JsonNode team = node.get("team");

                int id = team.get("id").asInt();
                String name = team.get("name").asText();

                teams.add(new TeamDTO(id, name));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return teams;
    }
}