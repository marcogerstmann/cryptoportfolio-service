package com.marcogerstmann.cryptoportfolio.service.service;

import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import java.math.BigDecimal;
import java.util.List;
import javax.money.MonetaryAmount;

public interface TransactionService {

    List<Transaction> getAll();

    MonetaryAmount calculateCostBasis(List<Transaction> transactions, String coinCode);

    BigDecimal calculateShares(List<Transaction> transactions, String coinCode);
}
