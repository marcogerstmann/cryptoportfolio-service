package com.marcogerstmann.cryptoportfolio.service.repository;

import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
