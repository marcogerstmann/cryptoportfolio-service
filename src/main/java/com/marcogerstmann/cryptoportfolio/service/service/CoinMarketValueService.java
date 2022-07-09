package com.marcogerstmann.cryptoportfolio.service.service;

import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import java.util.List;
import java.util.Set;

public interface CoinMarketValueService {

    List<CoinQuote> getCurrentCoinQuotes(Set<String> coinCodes);
}
