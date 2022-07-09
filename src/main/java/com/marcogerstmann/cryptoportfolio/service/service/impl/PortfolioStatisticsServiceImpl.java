package com.marcogerstmann.cryptoportfolio.service.service.impl;

import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import com.marcogerstmann.cryptoportfolio.service.dto.CoinPortfolioStatisticsDTO;
import com.marcogerstmann.cryptoportfolio.service.dto.OverallPortfolioStatisticsDTO;
import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import com.marcogerstmann.cryptoportfolio.service.service.CoinMarketValueService;
import com.marcogerstmann.cryptoportfolio.service.service.CoinService;
import com.marcogerstmann.cryptoportfolio.service.service.PortfolioStatisticsService;
import com.marcogerstmann.cryptoportfolio.service.service.TransactionService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import javax.money.MonetaryAmount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioStatisticsServiceImpl implements PortfolioStatisticsService {

    private final TransactionService transactionService;
    private final CoinService coinService;
    private final CoinMarketValueService coinMarketValueService;

    @Override
    public OverallPortfolioStatisticsDTO getOverallPortfolioStatistics() {
        // TODO CP-3 :: Do calculations based on coin statistics
        final List<CoinPortfolioStatisticsDTO> coinStatistics = getCoinPortolioStatistics();
        return null;

//        final Set<String> coinCodes = coinService.getCodes();
//        final Map<String, CoinQuote> coinQuotes = coinMarketValueService.getCurrentCoinQuotes(coinCodes);
//        final List<Transaction> transactions = transactionService.getAll();
//
//        final MonetaryAmount costBasis = calculateCostBasis(transactions);
//        final MonetaryAmount currentPortfolioValue = calculateCurrentPortfolioValue(transactions, coinQuotes);
//        final MonetaryAmount differenceAbsolute = calculateDifferenceAbsolute(costBasis, currentPortfolioValue);
//        final Double differenceRelative = calculateDifferenceRelative(costBasis, currentPortfolioValue);
//
//        return OverallPortfolioStatisticsDTO.builder()
//            .costBasis(costBasis)
//            .currentPortfolioValue(currentPortfolioValue)
//            .differenceAbsolute(differenceAbsolute)
//            .differencePercentage(differenceRelative)
//            .build();
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
        final List<Transaction> coinTransactions = transactions.stream()
            .filter(transaction -> coinQuote.getCoinCode().equals(transaction.getCoin().getCode()))
            .toList();

        final MonetaryAmount currentMarketPrice = coinQuote.getPrice();
        final MonetaryAmount costBasis = transactionService.calculateCostBasis(coinTransactions);
        final BigDecimal shares = transactionService.calculateShares(coinTransactions);
        final MonetaryAmount averageInvestedPrice = calculateAverageInvestedPrice(costBasis, shares);
        final MonetaryAmount currentValue = calculateCurrentValue(shares, coinQuote.getPrice());
        final MonetaryAmount differenceAbsolute = calculateDifferenceAbsolute(costBasis, currentValue);
        final BigDecimal differencePercentage = calculateDifferencePercentage(costBasis, currentValue);

        return CoinPortfolioStatisticsDTO.builder()
            .coinCode(coinQuote.getCoinCode())
            .coinName(coinQuote.getCoinName())
            .currentMarketPrice(currentMarketPrice)
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
        // TODO CP-3 :: Calculate relative difference
        return BigDecimal.TEN;
    }
}
