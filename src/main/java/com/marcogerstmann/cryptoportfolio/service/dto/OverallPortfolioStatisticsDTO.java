package com.marcogerstmann.cryptoportfolio.service.dto;

import java.math.BigDecimal;
import javax.money.MonetaryAmount;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OverallPortfolioStatisticsDTO {

    private MonetaryAmount costBasis;
    private MonetaryAmount currentPortfolioValue;
    private MonetaryAmount differenceAbsolute;
    private BigDecimal differencePercentage;
}
