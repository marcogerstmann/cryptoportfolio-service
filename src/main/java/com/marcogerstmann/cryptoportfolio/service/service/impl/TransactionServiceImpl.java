package com.marcogerstmann.cryptoportfolio.service.service.impl;

import static com.marcogerstmann.cryptoportfolio.service.enums.TransactionType.BUY;

import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import com.marcogerstmann.cryptoportfolio.service.enums.FiatCurrency;
import com.marcogerstmann.cryptoportfolio.service.repository.TransactionRepository;
import com.marcogerstmann.cryptoportfolio.service.service.TransactionService;
import java.math.BigDecimal;
import java.util.List;
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
    public MonetaryAmount calculateCostBasis(final List<Transaction> transactions) {
        final BigDecimal costBasis = transactions.stream()
            .filter(transaction -> BUY.equals(transaction.getType()))
            .map(Transaction::getFiatAmount)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);

        // TODO CP-18 :: Handle TRANSFER transacion type
        // TODO CP-19 :: Handle SWAP transacion type
        // TODO CP-15 :: Handle SELL transacion type

        return Money.of(costBasis, FiatCurrency.EUR.name());
    }

    @Override
    public BigDecimal calculateShares(final List<Transaction> transactions) {
        // TODO CP-18 :: Handle TRANSFER transacion type
        // TODO CP-19 :: Handle SWAP transacion type
        // TODO CP-15 :: Handle SELL transacion type
        return transactions.stream()
            .filter(transaction -> BUY.equals(transaction.getType()))
            .map(Transaction::getCoinAmount)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }
}
