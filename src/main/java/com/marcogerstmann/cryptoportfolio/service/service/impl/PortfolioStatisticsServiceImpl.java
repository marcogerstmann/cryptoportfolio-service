package com.marcogerstmann.cryptoportfolio.service.service.impl;

import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import com.marcogerstmann.cryptoportfolio.service.dto.CoinPortfolioStatisticsDTO;
import com.marcogerstmann.cryptoportfolio.service.dto.OverallPortfolioStatisticsDTO;
import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import com.marcogerstmann.cryptoportfolio.service.enums.FiatCurrency;
import com.marcogerstmann.cryptoportfolio.service.service.CoinMarketValueService;
import com.marcogerstmann.cryptoportfolio.service.service.CoinService;
import com.marcogerstmann.cryptoportfolio.service.service.PortfolioStatisticsService;
import com.marcogerstmann.cryptoportfolio.service.service.TransactionService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import javax.money.MonetaryAmount;
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
    public OverallPortfolioStatisticsDTO getOverallPortfolioStatistics() {
        final List<CoinPortfolioStatisticsDTO> coinStatistics = getCoinPortolioStatistics();

        final MonetaryAmount costBasis = calculateCostBasis(coinStatistics);
        final MonetaryAmount portfolioValue = calculatePortfolioValue(coinStatistics);
        final MonetaryAmount differenceAbsolute = calculateDifferenceAbsolute(costBasis, portfolioValue);
        final BigDecimal differencePercentage = calculateDifferencePercentage(costBasis, portfolioValue);

        return OverallPortfolioStatisticsDTO.builder()
            .costBasis(costBasis)
            .currentPortfolioValue(portfolioValue)
            .differenceAbsolute(differenceAbsolute)
            .differencePercentage(differencePercentage)
            .build();
    }

    @Override
    public List<CoinPortfolioStatisticsDTO> getCoinPortolioStatistics() {
        final Set<String> coinCodes = coinService.getCodes();
        final List<CoinQuote> coinQuotes = coinMarketValueService.getCurrentCoinQuotes(coinCodes);
        final List<Transaction> transactions = transactionService.getAll();

        return coinQuotes.stream()
            .map(coinQuote -> calculateCoinPortolioStatistics(coinQuote, transactions))
            .toList();
    }

    private CoinPortfolioStatisticsDTO calculateCoinPortolioStatistics(final CoinQuote coinQuote, final List<Transaction> transactions) {
        final MonetaryAmount costBasis = transactionService.calculateCostBasis(transactions, coinQuote.getCoinCode());
        final BigDecimal shares = transactionService.calculateShares(transactions, coinQuote.getCoinCode());
        final MonetaryAmount averageInvestedPrice = calculateAverageInvestedPrice(costBasis, shares);
        final MonetaryAmount currentValue = calculateCurrentValue(shares, coinQuote.getPrice());
        final MonetaryAmount differenceAbsolute = calculateDifferenceAbsolute(costBasis, currentValue);
        final BigDecimal differencePercentage = calculateDifferencePercentage(costBasis, currentValue);

        return CoinPortfolioStatisticsDTO.builder()
            .coinCode(coinQuote.getCoinCode())
            .coinName(coinQuote.getCoinName())
            .currentMarketPrice(coinQuote.getPrice())
            .shares(shares)
            .costBasis(costBasis)
            .averageInvestedPrice(averageInvestedPrice)
            .currentValue(currentValue)
            .differenceAbsolute(differenceAbsolute)
            .differencePercentage(differencePercentage)
            .build();
    }

    private MonetaryAmount calculateCurrentValue(final BigDecimal shares, final MonetaryAmount currentMarketPrice) {
        return currentMarketPrice.multiply(shares);
    }

    private MonetaryAmount calculateAverageInvestedPrice(final MonetaryAmount costBasis, final BigDecimal shares) {
        return costBasis.divide(shares);
    }

    private MonetaryAmount calculateDifferenceAbsolute(final MonetaryAmount costBasis, final MonetaryAmount currentValue) {
        return currentValue.subtract(costBasis);
    }

    private BigDecimal calculateDifferencePercentage(final MonetaryAmount costBasis, final MonetaryAmount currentValue) {
        final double cost = costBasis.getNumber().doubleValue();
        final double current = currentValue.getNumber().doubleValue();
        final double differencePercentage = (current - cost) / cost;

        return BigDecimal.valueOf(differencePercentage * 100);
    }

    private MonetaryAmount calculateCostBasis(final List<CoinPortfolioStatisticsDTO> coinStatistics) {
        return coinStatistics.stream()
            .map(CoinPortfolioStatisticsDTO::getCostBasis)
            .reduce(MonetaryAmount::add)
            .orElse(Money.of(0, FiatCurrency.EUR.name()));
    }

    private MonetaryAmount calculatePortfolioValue(final List<CoinPortfolioStatisticsDTO> coinStatistics) {
        return coinStatistics.stream()
            .map(CoinPortfolioStatisticsDTO::getCurrentValue)
            .reduce(MonetaryAmount::add)
            .orElse(Money.of(0, FiatCurrency.EUR.name()));
    }
}
