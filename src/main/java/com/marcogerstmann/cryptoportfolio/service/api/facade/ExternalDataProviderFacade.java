package com.marcogerstmann.cryptoportfolio.service.api.facade;

import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import java.util.List;
import java.util.Set;

public interface ExternalDataProviderFacade {

    List<CoinQuote> getCurrentCoinQuotes(final Set<String> coinCodes);
}
