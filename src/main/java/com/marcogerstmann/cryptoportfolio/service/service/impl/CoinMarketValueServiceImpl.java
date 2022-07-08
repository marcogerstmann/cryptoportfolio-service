package com.marcogerstmann.cryptoportfolio.service.service.impl;

import static java.util.Collections.emptyList;

import com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.service.CoinMarketCapService;
import com.marcogerstmann.cryptoportfolio.service.dto.CoinMarketValueDTO;
import com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.model.CmcQuoteResponse;
import com.marcogerstmann.cryptoportfolio.service.service.CoinMarketValueService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinMarketValueServiceImpl implements CoinMarketValueService {

    private final CoinMarketCapService coinMarketCapService;

    @Override
    public List<CoinMarketValueDTO> fetchCoinValues(final Set<String> coinCodes) {
        final CmcQuoteResponse cmcQuotes = coinMarketCapService.fetchCoinQuotes(coinCodes);
        return toCoinMarketValues(cmcQuotes);
    }

    private List<CoinMarketValueDTO> toCoinMarketValues(final CmcQuoteResponse cmcQuotes) {
        // TODO CP-9 :: map CoinMarketCap response to list of DTOs
        return emptyList();
    }
}
