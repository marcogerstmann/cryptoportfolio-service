package com.marcogerstmann.cryptoportfolio.service.controller;

import com.marcogerstmann.cryptoportfolio.service.entity.Coin;
import com.marcogerstmann.cryptoportfolio.service.service.CoinService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1.0/coin")
@AllArgsConstructor
public class CoinController {

    private final CoinService coinService;

    @GetMapping
    public List<Coin> getAllCoins() {
        return coinService.getAllCoins();
    }
}
