package com.marcogerstmann.cryptoportfolio.service.api.model;

import javax.money.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoinQuote {

    private String symbol;
    private MonetaryAmount price;
}
