package com.chrissytirocerto.model;

public class MatchStats {

    private String teamName;
    private double averageGoals;
    private double over25Frequency;

    public MatchStats(String teamName, double averageGoals, double over25Frequency) {
        this.teamName = teamName;
        this.averageGoals = averageGoals;
        this.over25Frequency = over25Frequency;
    }

    public String getTeamName() {
        return teamName;
    }

    public double getAverageGoals() {
        return averageGoals;
    }

    public double getOver25Frequency() {
        return over25Frequency;
    }
}