package com.marcogerstmann.cryptoportfolio.service.service;

import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import java.util.Map;
import java.util.Set;

public interface CoinMarketValueService {

    Map<String, CoinQuote> getCurrentCoinQuotes(Set<String> coinCodes);
}
