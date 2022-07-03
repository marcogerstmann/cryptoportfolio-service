package com.marcogerstmann.cryptoportfolio.service.repository;

import com.marcogerstmann.cryptoportfolio.service.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Long> {

}
