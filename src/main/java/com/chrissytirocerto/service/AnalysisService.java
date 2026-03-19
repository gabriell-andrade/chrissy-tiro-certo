package com.chrissytirocerto.service;

import com.chrissytirocerto.client.FootballApiClient;
import com.chrissytirocerto.model.MatchAnalysis;
import com.chrissytirocerto.model.MatchStats;
import com.chrissytirocerto.model.Suggestion;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalysisService {

    private final FootballApiClient client;

    private final Map<Integer, String> statsCache = new HashMap<>();

    public AnalysisService(FootballApiClient client) {
        this.client = client;
    }

    public MatchAnalysis analyze(MatchStats teamA, MatchStats teamB, int teamAId, int teamBId) {

        ObjectMapper mapper = new ObjectMapper();

        // ================= NOMES DOS TIMES =================
        String teamAName = "Time A";
        String teamBName = "Time B";

        try {
            JsonNode teamAJson = mapper.readTree(client.getTeamInfo(teamAId))
                    .get("response").get(0).get("team");
            teamAName = teamAJson.get("name").asText();

            JsonNode teamBJson = mapper.readTree(client.getTeamInfo(teamBId))
                    .get("response").get(0).get("team");
            teamBName = teamBJson.get("name").asText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String responseA = client.getLastMatches(teamAId);
        String responseB = client.getLastMatches(teamBId);

        // ================= TIME A =================
        double averageGoalsA = 0.0;
        double avgCornersA = 0;
        double avgCardsA = 0;

        try {
            JsonNode matchesA = mapper.readTree(responseA).get("response");

            int totalGoalsA = 0;
            int totalCornersA = 0;
            int totalCardsA = 0;
            int countA = 0;

            for (JsonNode match : matchesA) {

                int homeGoals = match.get("goals").get("home").asInt();
                int awayGoals = match.get("goals").get("away").asInt();

                int total = homeGoals + awayGoals;

                totalGoalsA += total;
                countA++;

                if (countA > 5) break;

                int fixtureId = match.get("fixture").get("id").asInt();

                String statsResponse;

                if (statsCache.containsKey(fixtureId)) {
                    statsResponse = statsCache.get(fixtureId);
                } else {
                    statsResponse = client.getMatchStatistics(fixtureId);
                    statsCache.put(fixtureId, statsResponse);
                }

                JsonNode statsArray = mapper.readTree(statsResponse).get("response");

                for (JsonNode teamStats : statsArray) {

                    int teamIdFromStats = teamStats.get("team").get("id").asInt();
                    if (teamIdFromStats != teamAId) continue;

                    for (JsonNode stat : teamStats.get("statistics")) {

                        String type = stat.get("type").asText().toLowerCase();
                        JsonNode valueNode = stat.get("value");

                        int value = (valueNode == null || valueNode.isNull()) ? 0 : valueNode.asInt();

                        if (type.contains("corner")) totalCornersA += value;
                        if (type.contains("yellow")) totalCardsA += value;
                    }
                }
            }

            if (countA > 0) {
                averageGoalsA = (double) totalGoalsA / countA;
                avgCornersA = (double) totalCornersA / countA;
                avgCardsA = (double) totalCardsA / countA;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ================= TIME B =================
        double averageGoalsB = 0.0;
        double avgCornersB = 0;
        double avgCardsB = 0;

        try {
            JsonNode matchesB = mapper.readTree(responseB).get("response");

            int totalGoalsB = 0;
            int totalCornersB = 0;
            int totalCardsB = 0;
            int countB = 0;

            for (JsonNode match : matchesB) {

                int homeGoals = match.get("goals").get("home").asInt();
                int awayGoals = match.get("goals").get("away").asInt();

                int total = homeGoals + awayGoals;

                totalGoalsB += total;
                countB++;

                if (countB > 5) break;

                int fixtureId = match.get("fixture").get("id").asInt();

                String statsResponse;

                if (statsCache.containsKey(fixtureId)) {
                    statsResponse = statsCache.get(fixtureId);
                } else {
                    statsResponse = client.getMatchStatistics(fixtureId);
                    statsCache.put(fixtureId, statsResponse);
                }

                JsonNode statsArray = mapper.readTree(statsResponse).get("response");

                for (JsonNode teamStats : statsArray) {

                    int teamIdFromStats = teamStats.get("team").get("id").asInt();
                    if (teamIdFromStats != teamBId) continue;

                    for (JsonNode stat : teamStats.get("statistics")) {

                        String type = stat.get("type").asText().toLowerCase();
                        JsonNode valueNode = stat.get("value");

                        int value = (valueNode == null || valueNode.isNull()) ? 0 : valueNode.asInt();

                        if (type.contains("corner")) totalCornersB += value;
                        if (type.contains("yellow")) totalCardsB += value;
                    }
                }
            }

            if (countB > 0) {
                averageGoalsB = (double) totalGoalsB / countB;
                avgCornersB = (double) totalCornersB / countB;
                avgCardsB = (double) totalCardsB / countB;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ================= MÉDIAS =================
        double goalsMedia = (averageGoalsA + averageGoalsB) / 2;
        double cornersMedia = (avgCornersA + avgCornersB) / 2;
        double cardsMedia = (avgCardsA + avgCardsB) / 2;

        MatchStats updatedTeamA = new MatchStats(
                teamAName,
                averageGoalsA,
                avgCornersA,
                avgCardsA
        );

        MatchStats updatedTeamB = new MatchStats(
                teamBName,
                averageGoalsB,
                avgCornersB,
                avgCardsB
        );

        // ================= SUGESTÕES =================
        List<Suggestion> suggestions = new ArrayList<>();

        // ================= GOLS =================
        if (goalsMedia < 1.4) {
            suggestions.add(new Suggestion("GOLS", "Under 3.5", 70));
        } else if (goalsMedia < 2.0) {
            suggestions.add(new Suggestion("GOLS", "Over 1.5", 65));
        } else if (goalsMedia < 2.6) {
            suggestions.add(new Suggestion("GOLS", "Over 2.5", 75));
        } else {
            suggestions.add(new Suggestion("GOLS", "Over 3.5", 80));
        }

        // ================= ESCANTEIOS =================
        if (cornersMedia < 4) {
            suggestions.add(new Suggestion("ESCANTEIOS", "Under 8.5", 65));
        } else if (cornersMedia < 6) {
            suggestions.add(new Suggestion("ESCANTEIOS", "Over 3.5", 60));
        } else if (cornersMedia < 9) {
            suggestions.add(new Suggestion("ESCANTEIOS", "Over 5.5", 70));
        } else {
            suggestions.add(new Suggestion("ESCANTEIOS", "Over 8.5", 80));
        }

        // ================= CARTÕES =================
        if (cardsMedia < 2) {
            suggestions.add(new Suggestion("CARTÕES", "Under 4.5", 65));
        } else if (cardsMedia < 3) {
            suggestions.add(new Suggestion("CARTÕES", "Over 1.5", 60));
        } else if (cardsMedia < 4) {
            suggestions.add(new Suggestion("CARTÕES", "Over 2.5", 70));
        } else {
            suggestions.add(new Suggestion("CARTÕES", "Over 3.5", 80));
        }

        return new MatchAnalysis(updatedTeamA, updatedTeamB, suggestions);
    }
}