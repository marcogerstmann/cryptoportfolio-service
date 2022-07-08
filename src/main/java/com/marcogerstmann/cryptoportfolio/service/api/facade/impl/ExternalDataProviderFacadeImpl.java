package com.marcogerstmann.cryptoportfolio.service.api.facade.impl;

import com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.model.CmcQuoteResponse;
import com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.model.CmcQuoteResponseData;
import com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.service.CoinMarketCapService;
import com.marcogerstmann.cryptoportfolio.service.api.facade.ExternalDataProviderFacade;
import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import com.marcogerstmann.cryptoportfolio.service.enums.FiatCurrency;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalDataProviderFacadeImpl implements ExternalDataProviderFacade {

    private final CoinMarketCapService coinMarketCapService;

    @Override
    public Map<String, CoinQuote> getCurrentCoinQuotes(final Set<String> coinCodes) {
        final CmcQuoteResponse cmcQuoteResponse = coinMarketCapService.fetchCoinQuotes(coinCodes);
        return toCoinQuotesMap(cmcQuoteResponse);
    }

    private Map<String, CoinQuote> toCoinQuotesMap(final CmcQuoteResponse cmcQuoteResponse) {
        final HashMap<String, CoinQuote> coinQuotesMap = new HashMap<>();
        cmcQuoteResponse.getData().forEach((key, value) -> coinQuotesMap.put(key, toCoinQuote(value)));
        return coinQuotesMap;
    }

    private CoinQuote toCoinQuote(final CmcQuoteResponseData cmcQuoteResponseData) {
        return new CoinQuote(
            cmcQuoteResponseData.getSymbol(),
            Money.of(cmcQuoteResponseData.getQuote().get(FiatCurrency.EUR).getPrice(), FiatCurrency.EUR.name())
        );
    }
}
