package com.chrissytirocerto.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.chrissytirocerto.model.MatchAnalysis;
import com.chrissytirocerto.model.MatchStats;
import com.chrissytirocerto.model.Suggestion;

@Service
public class AnalysisService {

    public MatchAnalysis analyze(MatchStats teamA, MatchStats teamB) {

        List<Suggestion> suggestions = new ArrayList<>();

        double totalAverageGoals = teamA.getAverageGoals() + teamB.getAverageGoals();
        double avgOver25 = (teamA.getOver25Frequency() + teamB.getOver25Frequency()) / 2;

        // 🎯 Over 2.5 gols
        if (totalAverageGoals >= 2.8 && avgOver25 >= 0.6) {
            suggestions.add(new Suggestion(
                    "Over 2.5 gols",
                    avgOver25 * 100,
                    getConfidence(avgOver25)
            ));
        }

        // 🎯 Over 1.5 gols
        if (totalAverageGoals >= 2.0) {
            suggestions.add(new Suggestion(
                    "Over 1.5 gols",
                    85,
                    "Alta"
            ));
        }

        // 🎯 Ambos marcam
        if (teamA.getAverageGoals() >= 1.2 && teamB.getAverageGoals() >= 1.2) {
            suggestions.add(new Suggestion(
                    "Ambos marcam",
                    70,
                    "Média"
            ));
        }

        // ⚠️ Under 2.5
        if (totalAverageGoals < 2.0) {
            suggestions.add(new Suggestion(
                    "Under 2.5 gols",
                    65,
                    "Média"
            ));
        }

        return new MatchAnalysis(teamA, teamB, suggestions);
    }

    private String getConfidence(double probability) {
        if (probability >= 0.75) return "Alta";
        if (probability >= 0.6) return "Média";
        return "Baixa";
    }
}
