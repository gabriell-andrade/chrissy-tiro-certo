package com.chrissytirocerto.model;

public class Suggestion {

    private String type;
    private double probability;
    private String confidence;

    public Suggestion(String type, double probability, String confidence) {
        this.type = type;
        this.probability = probability;
        this.confidence = confidence;
    }

    public String getType() {
        return type;
    }

    public double getProbability() {
        return probability;
    }

    public String getConfidence() {
        return confidence;
    }
}
