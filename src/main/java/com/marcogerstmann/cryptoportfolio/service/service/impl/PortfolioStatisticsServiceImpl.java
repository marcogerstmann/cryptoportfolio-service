package com.marcogerstmann.cryptoportfolio.service.service.impl;

import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import com.marcogerstmann.cryptoportfolio.service.dto.OverallPortfolioStatisticsDTO;
import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import com.marcogerstmann.cryptoportfolio.service.enums.FiatCurrency;
import com.marcogerstmann.cryptoportfolio.service.service.CoinMarketValueService;
import com.marcogerstmann.cryptoportfolio.service.service.CoinService;
import com.marcogerstmann.cryptoportfolio.service.service.PortfolioStatisticsService;
import com.marcogerstmann.cryptoportfolio.service.service.TransactionService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioStatisticsServiceImpl implements PortfolioStatisticsService {

    private final TransactionService transactionService;
    private final CoinService coinService;
    private final CoinMarketValueService coinMarketValueService;

    @Override
    public OverallPortfolioStatisticsDTO calculateOverallPortfolioStatistics() {
        final Set<String> coinCodes = coinService.getCodes();
        final Map<String, CoinQuote> coinQuotes = coinMarketValueService.getCurrentCoinQuotes(coinCodes);
        final List<Transaction> transactions = transactionService.getAll();

        return new OverallPortfolioStatisticsDTO(Money.of(42, FiatCurrency.EUR.name()));
    }
}
