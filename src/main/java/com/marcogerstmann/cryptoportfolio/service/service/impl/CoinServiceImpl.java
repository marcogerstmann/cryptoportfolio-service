package com.marcogerstmann.cryptoportfolio.service.service.impl;

import com.marcogerstmann.cryptoportfolio.service.entity.Coin;
import com.marcogerstmann.cryptoportfolio.service.repository.CoinRepository;
import com.marcogerstmann.cryptoportfolio.service.service.CoinService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinServiceImpl implements CoinService {

    private final CoinRepository coinRepository;

    @Override
    public List<Coin> getAll() {
        return coinRepository.findAll();
    }

    @Override
    public Set<String> getCodes() {
        return getAll().stream()
            .map(Coin::getCode)
            .collect(Collectors.toSet());
    }
}
