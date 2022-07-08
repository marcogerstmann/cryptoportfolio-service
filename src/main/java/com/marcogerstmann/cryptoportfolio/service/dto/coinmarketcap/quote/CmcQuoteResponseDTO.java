package com.marcogerstmann.cryptoportfolio.service.dto.coinmarketcap.quote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.LinkedHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmcQuoteResponseDTO {

    private LinkedHashMap<String, CmcQuoteResponseDataDTO> data;
}
