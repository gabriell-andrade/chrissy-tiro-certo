package com.chrissytirocerto.controller;

import org.springframework.web.bind.annotation.*;

import com.chrissytirocerto.model.MatchAnalysis;
import com.chrissytirocerto.model.MatchStats;
import com.chrissytirocerto.service.AnalysisService;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    private final AnalysisService service;

    public AnalysisController(AnalysisService service) {
        this.service = service;
    }

    @GetMapping
    public MatchAnalysis analyzeMatch() {

        // 🔥 DADOS MOCKADOS (simulação)
        MatchStats teamA = new MatchStats("Time A", 1.8, 0.8);
        MatchStats teamB = new MatchStats("Time B", 1.6, 0.7);

        return service.analyze(teamA, teamB);
    }
}
