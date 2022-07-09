package com.marcogerstmann.cryptoportfolio.service.service.impl;

import static com.marcogerstmann.cryptoportfolio.service.enums.TransactionType.BUY;

import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import com.marcogerstmann.cryptoportfolio.service.dto.OverallPortfolioStatisticsDTO;
import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import com.marcogerstmann.cryptoportfolio.service.enums.FiatCurrency;
import com.marcogerstmann.cryptoportfolio.service.service.CoinMarketValueService;
import com.marcogerstmann.cryptoportfolio.service.service.CoinService;
import com.marcogerstmann.cryptoportfolio.service.service.PortfolioStatisticsService;
import com.marcogerstmann.cryptoportfolio.service.service.TransactionService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
    public OverallPortfolioStatisticsDTO calculateOverallPortfolioStatistics() {
        final Set<String> coinCodes = coinService.getCodes();
        final Map<String, CoinQuote> coinQuotes = coinMarketValueService.getCurrentCoinQuotes(coinCodes);
        final List<Transaction> transactions = transactionService.getAll();

        final MonetaryAmount costBasis = calculateCostBasis(transactions);
        final MonetaryAmount currentPortfolioValue = calculateCurrentPortfolioValue(transactions, coinQuotes);
        final MonetaryAmount differenceAbsolute = calculateDifferenceAbsolute(costBasis, currentPortfolioValue);
        final Double differenceRelative = calculateDifferenceRelative(costBasis, currentPortfolioValue);

        return OverallPortfolioStatisticsDTO.builder()
            .costBasis(costBasis)
            .currentPortfolioValue(currentPortfolioValue)
            .differenceAbsolute(differenceAbsolute)
            .differencePercentage(differenceRelative)
            .build();
    }

    private MonetaryAmount calculateCostBasis(final List<Transaction> transactions) {
        final BigDecimal costBasis = transactions.stream()
            .filter(transaction -> BUY.equals(transaction.getType()))
            .map(Transaction::getFiatAmount)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);

        // TODO CP-15 :: What to do with SELL and TRANSFER transaction types?

        return Money.of(costBasis, FiatCurrency.EUR.name());
    }

    private MonetaryAmount calculateCurrentPortfolioValue(final List<Transaction> transactions, final Map<String, CoinQuote> coinQuotes) {
        // TODO CP-3 :: Calculate current portfolio value
        return Money.of(BigDecimal.ZERO, FiatCurrency.EUR.name());
    }

    private MonetaryAmount calculateDifferenceAbsolute(final MonetaryAmount costBasis, final MonetaryAmount currentPortfolioValue) {
        // TODO CP-3 :: Calculate absolute difference
        return Money.of(-42.42, FiatCurrency.EUR.name());
    }

    private double calculateDifferenceRelative(final MonetaryAmount costBasis, final MonetaryAmount currentPortfolioValue) {
        // TODO CP-3 :: Calculate relative difference
        return -42.42D;
    }
}
