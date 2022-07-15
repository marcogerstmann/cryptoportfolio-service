package com.marcogerstmann.cryptoportfolio.service.service.impl;

import static com.marcogerstmann.cryptoportfolio.service.enums.TransactionType.BUY;

import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import com.marcogerstmann.cryptoportfolio.service.enums.FiatCurrency;
import com.marcogerstmann.cryptoportfolio.service.repository.TransactionRepository;
import com.marcogerstmann.cryptoportfolio.service.service.TransactionService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.money.MonetaryAmount;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public MonetaryAmount calculateCostBasis(final List<Transaction> transactions, final String coinCode) {
        final List<Transaction> coinTransactions = filterTransactionsByCoin(transactions, coinCode);
        final MonetaryAmount totalFiatBuyAmount = getTotalFiatBuyAmount(coinTransactions);
        final MonetaryAmount totalFiatFeeAmount = getTotalFiatFeeAmount(coinTransactions);

        return totalFiatBuyAmount.add(totalFiatFeeAmount);
    }

    private MonetaryAmount getTotalFiatBuyAmount(final List<Transaction> transactions) {
        final BigDecimal totalBuy = transactions.stream()
            .filter(transaction -> BUY.equals(transaction.getType()))
            .map(Transaction::getFiatAmount)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);

        return Money.of(totalBuy, FiatCurrency.EUR.name());
    }

    private MonetaryAmount getTotalFiatFeeAmount(final List<Transaction> transactions) {
        final BigDecimal totalFees = transactions.stream()
            .map(Transaction::getFeeFiatAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);

        return Money.of(totalFees, FiatCurrency.EUR.name());
    }

    @Override
    public BigDecimal calculateShares(final List<Transaction> transactions, final String coinCode) {
        final List<Transaction> coinTransactions = filterTransactionsByCoin(transactions, coinCode);
        final BigDecimal totalBuyCoinAmount = getTotalBuyCoinAmount(coinTransactions);
        final BigDecimal totalFeeCoinAmount = getTotalFeeCoinAmount(coinTransactions);

        return totalBuyCoinAmount.subtract(totalFeeCoinAmount);
    }

    private BigDecimal getTotalBuyCoinAmount(final List<Transaction> transactions) {
        return transactions.stream()
            .filter(transaction -> BUY.equals(transaction.getType()))
            .map(Transaction::getCoinAmount)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getTotalFeeCoinAmount(final List<Transaction> transactions) {
        return transactions.stream()
            .map(Transaction::getFeeCoinAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }

    private List<Transaction> filterTransactionsByCoin(final List<Transaction> transactions, final String coinCode) {
        return transactions.stream()
            .filter(transaction -> coinCode.equals(transaction.getCoin().getCode()))
            .toList();
    }
}
