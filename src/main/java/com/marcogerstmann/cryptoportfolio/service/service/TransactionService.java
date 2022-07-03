package com.marcogerstmann.cryptoportfolio.service.service;

import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import java.util.List;

public interface TransactionService {

    List<Transaction> getAll();
}
