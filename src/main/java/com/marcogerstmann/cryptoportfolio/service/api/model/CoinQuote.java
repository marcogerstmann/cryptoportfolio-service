package com.marcogerstmann.cryptoportfolio.service.api.model;

import javax.money.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CoinQuote {

    private String coinCode;
    private String coinName;
    private MonetaryAmount price;
}
