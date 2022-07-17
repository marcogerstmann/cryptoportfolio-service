package com.marcogerstmann.cryptoportfolio.service.controller;

import com.marcogerstmann.cryptoportfolio.service.dto.CoinPortfolioStatisticsDTO;
import com.marcogerstmann.cryptoportfolio.service.dto.OverallPortfolioStatisticsDTO;
import com.marcogerstmann.cryptoportfolio.service.service.PortfolioStatisticsService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1.0/portfolio/statistics")
@AllArgsConstructor
@CrossOrigin
public class PortfolioStatisticsController {

    private final PortfolioStatisticsService portfolioStatisticsService;

    @GetMapping
    public OverallPortfolioStatisticsDTO getOverallPortfolioStatistics() {
        return portfolioStatisticsService.getOverallPortfolioStatistics();
    }

    @GetMapping(value = "/coin")
    public List<CoinPortfolioStatisticsDTO> getCoinPortfolioStatistics() {
        return portfolioStatisticsService.getCoinPortolioStatistics();
    }
}
