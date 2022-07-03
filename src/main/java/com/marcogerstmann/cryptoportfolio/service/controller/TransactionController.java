package com.marcogerstmann.cryptoportfolio.service.controller;

import com.marcogerstmann.cryptoportfolio.service.dto.TransactionDto;
import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import com.marcogerstmann.cryptoportfolio.service.mapper.TransactionMapper;
import com.marcogerstmann.cryptoportfolio.service.service.TransactionService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1.0/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    @GetMapping
    public List<TransactionDto> getAllCoins() {
        final List<Transaction> transactions = transactionService.getAll();
        return transactionMapper.toDto(transactions);
    }
}
