package com.chrissytirocerto.model;

public class Suggestion {

    private String market;
    private String pick;
    private double confidence;

    public Suggestion(String market, String pick, double confidence) {
        this.market = market;
        this.pick = pick;
        this.confidence = confidence;
    }

    public String getMarket() {
        return market;
    }

    public String getPick() {
        return pick;
    }

    public double getConfidence() {
        return confidence;
    }
}
