package com.chrissytirocerto.model;

public class MatchStats {

    private String teamName;
    private double averageGoals;
    private double averageCorners;
    private double averageCards;

    public MatchStats(String teamName,
                      double averageGoals,
                      double averageCorners,
                      double averageCards) {
        this.teamName = teamName;
        this.averageGoals = averageGoals;
        this.averageCorners = averageCorners;
        this.averageCards = averageCards;
    }

    public String getTeamName() {
        return teamName;
    }

    public double getAverageGoals() {
        return averageGoals;
    }

    public double getAverageCorners() {
        return averageCorners;
    }

    public double getAverageCards() {
        return averageCards;
    }
}