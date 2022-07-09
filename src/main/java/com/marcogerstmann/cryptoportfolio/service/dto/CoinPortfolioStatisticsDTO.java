package com.marcogerstmann.cryptoportfolio.service.dto;

import java.math.BigDecimal;
import javax.money.MonetaryAmount;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoinPortfolioStatisticsDTO {

    private String coinCode;
    private String coinName;
    private MonetaryAmount currentMarketPrice;
    private BigDecimal shares;
    private MonetaryAmount costBasis;
    private MonetaryAmount currentValue;
    private MonetaryAmount averageInvestedPrice;
    private MonetaryAmount differenceAbsolute;
    private BigDecimal differencePercentage;
}
