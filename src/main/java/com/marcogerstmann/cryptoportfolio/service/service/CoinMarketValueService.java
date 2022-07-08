package com.marcogerstmann.cryptoportfolio.service.service;

import com.marcogerstmann.cryptoportfolio.service.dto.CoinMarketValueDTO;
import java.util.List;
import java.util.Set;

public interface CoinMarketValueService {

    List<CoinMarketValueDTO> fetchCoinValues(Set<String> coinCodes);
}
