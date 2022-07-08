package com.marcogerstmann.cryptoportfolio.service.api.coinmarketcap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmcPriceQuote {

    private Double price;

    @JsonProperty(value = "percent_change_1h")
    private Double percentChange1h;

    @JsonProperty(value = "percent_change_24h")
    private Double percentChange24h;

    @JsonProperty(value = "percent_change_7d")
    private Double percentChange7d;

    @JsonProperty(value = "percent_change_30d")
    private Double percentChange30d;
}
