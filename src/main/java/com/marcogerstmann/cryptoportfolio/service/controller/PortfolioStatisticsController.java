package com.marcogerstmann.cryptoportfolio.service.controller;

import com.marcogerstmann.cryptoportfolio.service.dto.OverallPortfolioStatisticsDTO;
import com.marcogerstmann.cryptoportfolio.service.service.PortfolioStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1.0/portfolio/statistics")
@AllArgsConstructor
public class PortfolioStatisticsController {

    private final PortfolioStatisticsService portfolioStatisticsService;

    @GetMapping
    public OverallPortfolioStatisticsDTO getOverallPortfolioStatistics() {
        return portfolioStatisticsService.calculateOverallPortfolioStatistics();
    }
}
