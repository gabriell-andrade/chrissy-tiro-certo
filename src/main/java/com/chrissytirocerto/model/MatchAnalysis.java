package com.chrissytirocerto.model;

import java.util.List;

public class MatchAnalysis {

    private MatchStats teamA;
    private MatchStats teamB;
    private List<Suggestion> suggestions;

    public MatchAnalysis(MatchStats teamA, MatchStats teamB, List<Suggestion> suggestions) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.suggestions = suggestions;
    }

    public MatchStats getTeamA() {
        return teamA;
    }

    public MatchStats getTeamB() {
        return teamB;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }
}
