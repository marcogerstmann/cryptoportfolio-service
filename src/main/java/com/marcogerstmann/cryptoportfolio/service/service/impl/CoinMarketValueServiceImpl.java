package com.marcogerstmann.cryptoportfolio.service.service.impl;

import com.marcogerstmann.cryptoportfolio.service.api.facade.ExternalDataProviderFacade;
import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import com.marcogerstmann.cryptoportfolio.service.service.CoinMarketValueService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinMarketValueServiceImpl implements CoinMarketValueService {

    private final ExternalDataProviderFacade externalDataProvider;

    @Override
    public List<CoinQuote> getCurrentCoinQuotes(final Set<String> coinCodes) {
        return externalDataProvider.getCurrentCoinQuotes(coinCodes);
    }
}
