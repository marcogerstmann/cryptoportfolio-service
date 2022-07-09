package com.marcogerstmann.cryptoportfolio.service.api.model;

import javax.money.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoinQuote {

    private String coinCode;
    private String coinName;
    private MonetaryAmount price;
}
