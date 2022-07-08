package com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.service.impl;

import static com.marcogerstmann.cryptoportfolio.service.util.CustomHttpHeaders.X_CMC_PRO_API_KEY;

import com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.model.CmcQuoteResponse;
import com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.service.CoinMarketCapService;
import com.marcogerstmann.cryptoportfolio.service.enums.FiatCurrency;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinMarketCapServiceImpl implements CoinMarketCapService {

    @Value("#{'${api.coinmarketcap.use-production}' == 'true'"
        + "? '${api.coinmarketcap.pro.url}'"
        + ": '${api.coinmarketcap.sandbox.url}'}")
    private String baseUrl;

    @Value("#{'${api.coinmarketcap.use-production}' == 'true'"
        + "? '${api.coinmarketcap.pro.api-key}'"
        + ": '${api.coinmarketcap.sandbox.api-key}'}")
    private String apiKey;

    @Override
    public CmcQuoteResponse fetchCoinQuotes(final Set<String> coinCodes) {
        final RestTemplate restTemplate = new RestTemplate();

        final String url = buildUrl();
        final Map<String, String> uriVariables = buildUriVariables(coinCodes);
        final HttpHeaders requestHeaders = buildRequestHeaders();
        final MultiValueMap<String, String> requestBody = buildRequestBody();

        final HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        final ResponseEntity<CmcQuoteResponse> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
            CmcQuoteResponse.class, uriVariables);

        return response.getBody();
    }

    private String buildUrl() {
        return baseUrl + "/v1/cryptocurrency/quotes/latest?symbol={symbol}&convert={convert}";
    }

    private Map<String, String> buildUriVariables(final Set<String> coinCodes) {
        final Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("symbol", String.join(",", coinCodes));
        uriVariables.put("convert", FiatCurrency.EUR.name());
        return uriVariables;
    }

    private HttpHeaders buildRequestHeaders() {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set(X_CMC_PRO_API_KEY, apiKey);
        return requestHeaders;
    }

    private MultiValueMap<String, String> buildRequestBody() {
        final MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("start", "1");
        requestBody.add("limit", "5000");
        return requestBody;
    }
}
