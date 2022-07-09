package com.marcogerstmann.cryptoportfolio.service.dto;

import javax.money.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OverallPortfolioStatisticsDTO {

    private MonetaryAmount costBasis;
}
