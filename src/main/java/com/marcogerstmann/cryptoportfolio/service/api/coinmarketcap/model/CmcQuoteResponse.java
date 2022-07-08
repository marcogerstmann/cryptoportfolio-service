package com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmcQuoteResponse {

    private Map<String, CmcQuoteResponseData> data;
}
