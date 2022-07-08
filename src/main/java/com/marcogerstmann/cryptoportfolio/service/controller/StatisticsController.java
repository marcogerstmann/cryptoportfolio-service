package com.marcogerstmann.cryptoportfolio.service.controller;

import com.marcogerstmann.cryptoportfolio.service.dto.CoinMarketValueDTO;
import com.marcogerstmann.cryptoportfolio.service.service.CoinMarketValueService;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1.0/statistics")
@AllArgsConstructor
public class StatisticsController {

    private final CoinMarketValueService coinMarketValueService;

    @GetMapping
    public List<CoinMarketValueDTO> getOverallStatistics() {
        final List<CoinMarketValueDTO> coinMarketValues = coinMarketValueService.fetchCoinValues(Set.of("BTC", "ETH", "DOT", "ADA"));
        return coinMarketValues;
    }
}
