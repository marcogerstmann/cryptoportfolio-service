package com.marcogerstmann.cryptoportfolio.service.api.facade;

import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import java.util.Map;
import java.util.Set;

public interface ExternalDataProviderFacade {

    Map<String, CoinQuote> getCurrentCoinQuotes(final Set<String> coinCodes);
}
