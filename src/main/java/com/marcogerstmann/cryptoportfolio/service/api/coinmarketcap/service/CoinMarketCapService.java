package com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.service;

import com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.model.CmcQuoteResponse;
import java.util.Set;

public interface CoinMarketCapService {

    CmcQuoteResponse fetchCoinQuotes(Set<String> coinCodes);
}
