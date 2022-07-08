package com.marcogerstmann.cryptoportfolio.service.controller;

import com.marcogerstmann.cryptoportfolio.service.dto.CoinMarketValueDTO;
import com.marcogerstmann.cryptoportfolio.service.service.CoinMarketValueService;
import java.util.Collections;
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
    public CoinMarketValueDTO getOverallStatistics() {
        final CoinMarketValueDTO coinMarketValue = coinMarketValueService.fetchCoinValues(Collections.emptySet());
        return coinMarketValue;
    }
}
