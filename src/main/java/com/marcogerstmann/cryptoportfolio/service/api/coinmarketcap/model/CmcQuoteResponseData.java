package com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marcogerstmann.cryptoportfolio.service.enums.FiatCurrency;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmcQuoteResponseData {

    private Integer id;
    private String name;
    private String symbol;
    private String slug;
    private Map<FiatCurrency, CmcPriceQuote> quote;
}
