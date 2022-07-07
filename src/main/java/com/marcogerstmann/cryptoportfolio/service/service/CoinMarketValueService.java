package com.marcogerstmann.cryptoportfolio.service.service;

import com.marcogerstmann.cryptoportfolio.service.dto.CoinMarketValueDto;
import java.util.Set;

public interface CoinMarketValueService {

    CoinMarketValueDto fetchCoinValues(final Set<String> coinCodes);
}
