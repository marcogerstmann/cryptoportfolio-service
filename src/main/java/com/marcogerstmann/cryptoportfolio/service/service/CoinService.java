package com.marcogerstmann.cryptoportfolio.service.service;

import com.marcogerstmann.cryptoportfolio.service.entity.Coin;
import java.util.List;
import java.util.Set;

public interface CoinService {

    List<Coin> getAll();

    Set<String> getCodes();
}
