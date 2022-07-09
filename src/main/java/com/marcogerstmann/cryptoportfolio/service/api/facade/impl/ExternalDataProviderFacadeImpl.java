package com.marcogerstmann.cryptoportfolio.service.api.facade.impl;

import com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.model.CmcQuoteResponse;
import com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.model.CmcQuoteResponseData;
import com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.service.CoinMarketCapService;
import com.marcogerstmann.cryptoportfolio.service.api.facade.ExternalDataProviderFacade;
import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import com.marcogerstmann.cryptoportfolio.service.enums.FiatCurrency;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalDataProviderFacadeImpl implements ExternalDataProviderFacade {

    private final CoinMarketCapService coinMarketCapService;

    @Override
    public List<CoinQuote> getCurrentCoinQuotes(final Set<String> coinCodes) {
        final CmcQuoteResponse cmcQuoteResponse = coinMarketCapService.fetchCoinQuotes(coinCodes);
        return toCoinQuotesList(cmcQuoteResponse);
    }

    private List<CoinQuote> toCoinQuotesList(final CmcQuoteResponse cmcQuoteResponse) {
        return cmcQuoteResponse.getData().values().stream()
            .map(this::toCoinQuote)
            .toList();
    }

    private CoinQuote toCoinQuote(final CmcQuoteResponseData cmcQuoteResponseData) {
        return new CoinQuote(
            cmcQuoteResponseData.getSymbol(),
            cmcQuoteResponseData.getName(),
            Money.of(cmcQuoteResponseData.getQuote().get(FiatCurrency.EUR).getPrice(), FiatCurrency.EUR.name())
        );
    }
}
