package com.marcogerstmann.cryptoportfolio.service.service.impl;

import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import com.marcogerstmann.cryptoportfolio.service.dto.OverallPortfolioStatisticsDTO;
import com.marcogerstmann.cryptoportfolio.service.service.CoinMarketValueService;
import com.marcogerstmann.cryptoportfolio.service.service.PortfolioStatisticsService;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioStatisticsServiceImpl implements PortfolioStatisticsService {

    private final CoinMarketValueService coinMarketValueService;

    @Override
    public OverallPortfolioStatisticsDTO getOverallPortfolioStatistics() {
        final Map<String, CoinQuote> coinQuotes = coinMarketValueService.getCurrentCoinQuotes(Set.of("BTC", "ETH", "DOT", "ADA"));
        return new OverallPortfolioStatisticsDTO("Hello World");
    }
}
