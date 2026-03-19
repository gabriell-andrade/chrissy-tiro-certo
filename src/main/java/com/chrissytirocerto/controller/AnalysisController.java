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
    public MatchAnalysis analyzeMatch(
            @RequestParam int teamA,
            @RequestParam int teamB
    ) {

        MatchStats statsA = new MatchStats("Time A", 0, 0, 0);
        MatchStats statsB = new MatchStats("Time B", 0, 0, 0);

        return service.analyze(statsA, statsB, teamA, teamB);
    }
}
