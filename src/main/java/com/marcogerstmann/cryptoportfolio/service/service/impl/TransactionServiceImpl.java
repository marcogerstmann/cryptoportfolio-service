package com.marcogerstmann.cryptoportfolio.service.service.impl;

import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import com.marcogerstmann.cryptoportfolio.service.repository.TransactionRepository;
import com.marcogerstmann.cryptoportfolio.service.service.TransactionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }
}
