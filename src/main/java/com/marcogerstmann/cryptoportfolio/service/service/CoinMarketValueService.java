package com.marcogerstmann.cryptoportfolio.service.service;

import com.marcogerstmann.cryptoportfolio.service.dto.CoinMarketValueDTO;
import java.util.Set;

public interface CoinMarketValueService {

    CoinMarketValueDTO fetchCoinValues(final Set<String> coinCodes);
}
