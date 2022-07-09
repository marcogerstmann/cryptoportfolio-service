package com.marcogerstmann.cryptoportfolio.service.service;

import com.marcogerstmann.cryptoportfolio.service.dto.CoinPortfolioStatisticsDTO;
import com.marcogerstmann.cryptoportfolio.service.dto.OverallPortfolioStatisticsDTO;
import java.util.List;

public interface PortfolioStatisticsService {

    OverallPortfolioStatisticsDTO getOverallPortfolioStatistics();

    List<CoinPortfolioStatisticsDTO> getCoinPortolioStatistics();
}
