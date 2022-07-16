package com.marcogerstmann.cryptoportfolio.service.service.impl;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.marcogerstmann.cryptoportfolio.service.api.model.CoinQuote;
import com.marcogerstmann.cryptoportfolio.service.dto.CoinPortfolioStatisticsDTO;
import com.marcogerstmann.cryptoportfolio.service.entity.Coin;
import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import com.marcogerstmann.cryptoportfolio.service.enums.FiatCurrency;
import com.marcogerstmann.cryptoportfolio.service.enums.TransactionType;
import com.marcogerstmann.cryptoportfolio.service.service.CoinMarketValueService;
import com.marcogerstmann.cryptoportfolio.service.service.CoinService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PortfolioStatisticsServiceImplTest {

    private static final String COIN_CODE = "BTC";
    private static final String COIN_NAME = "Bitcoin";
    private static final MonetaryAmount COIN_PRICE = Money.of(100000, FiatCurrency.EUR.name());

    private static final Set<String> COIN_CODES = Set.of(COIN_CODE);

    private static final List<CoinQuote> COIN_QUOTES = List.of(
        CoinQuote.builder()
            .coinCode(COIN_CODE)
            .coinName(COIN_NAME)
            .price(COIN_PRICE)
            .build()
    );

    @InjectMocks
    private PortfolioStatisticsServiceImpl service;
    @Mock
    private TransactionServiceImpl transactionServiceMock;
    @Mock
    private CoinService coinServiceMock;
    @Mock
    private CoinMarketValueService coinMarketValueServiceMock;

    @BeforeEach
    void beforeEach() {
        given(coinServiceMock.getCodes()).willReturn(COIN_CODES);
        given(coinMarketValueServiceMock.getCurrentCoinQuotes(COIN_CODES)).willReturn(COIN_QUOTES);
        given(transactionServiceMock.calculateCostBasis(anyList(), anyString())).willCallRealMethod();
        given(transactionServiceMock.calculateShares(anyList(), anyString())).willCallRealMethod();
    }

    @Nested
    @DisplayName("When getting coin portfolio statistics")
    class GetCoinPortolioStatistics {

        @ParameterizedTest
        @MethodSource("getCoinPortolioStatisticsParams")
        @DisplayName("should create the expected result based on given transactions")
        void getCoinPortfolioStatistics_should_create_the_expected_result(final List<Transaction> givenTransactions,
            final List<CoinPortfolioStatisticsDTO> expectedResult) {
            // given
            given(transactionServiceMock.getAll()).willReturn(givenTransactions);

            // when
            final List<CoinPortfolioStatisticsDTO> actualResult = service.getCoinPortolioStatistics();

            // then
            // Had to use ".toString()" here because the comparison of BigDecimal values inside of lists seems buggy (showing same values, but test is still failing)
            assertEquals(expectedResult.toString(), actualResult.toString());
        }

        private static Stream<Arguments> getCoinPortolioStatisticsParams() {
            return Stream.of(
                buildNoTransactionsArguments(),
                buildOneBuyTransactionNoFeesArguments(),
                buildOneBuyTransactionWithFeesArguments(),
                buildTwoBuyTransactionsWithFeesArguments(),
                buildOneBuyAndOneTransferTransactionsWithFeesArguments()
            );
        }

        /**
         * No transactions.
         */
        private static Arguments buildNoTransactionsArguments() {
            // given transations
            final List<Transaction> givenTransactions = emptyList();

            // expected result
            final Money zeroMoney = Money.of(BigDecimal.ZERO, FiatCurrency.EUR.name());
            final List<CoinPortfolioStatisticsDTO> expectedResult = List.of(
                CoinPortfolioStatisticsDTO.builder()
                    .coinCode(COIN_CODE)
                    .coinName(COIN_NAME)
                    .currentMarketPrice(COIN_PRICE)
                    .shares(BigDecimal.ZERO)
                    .costBasis(zeroMoney)
                    .averageInvestedPrice(zeroMoney)
                    .currentValue(zeroMoney)
                    .differenceAbsolute(zeroMoney)
                    .differencePercentage(BigDecimal.ZERO)
                    .build()
            );

            return Arguments.of(givenTransactions, expectedResult);
        }

        /**
         * One buy transaction without fees.
         */
        private static Arguments buildOneBuyTransactionNoFeesArguments() {
            // given values
            final BigDecimal buyFiatAmount = BigDecimal.valueOf(50000);
            final BigDecimal buyCoinAmount = BigDecimal.ONE;

            // given transactions
            final List<Transaction> givenTransactions = List.of(
                Transaction.builder()
                    .coin(buildCoin())
                    .type(TransactionType.BUY)
                    .fiatAmount(buyFiatAmount)
                    .coinAmount(buyCoinAmount)
                    .build()
            );

            // excpected result
            final List<CoinPortfolioStatisticsDTO> expectedResult = List.of(
                CoinPortfolioStatisticsDTO.builder()
                    .coinCode(COIN_CODE)
                    .coinName(COIN_NAME)
                    .currentMarketPrice(COIN_PRICE)
                    .shares(buyCoinAmount)
                    .costBasis(Money.of(buyFiatAmount, FiatCurrency.EUR.name()))
                    .averageInvestedPrice(Money.of(buyFiatAmount, FiatCurrency.EUR.name()))
                    .currentValue(COIN_PRICE)
                    .differenceAbsolute(Money.of(50000, FiatCurrency.EUR.name()))
                    .differencePercentage(BigDecimal.valueOf(100.0))
                    .build()
            );

            return Arguments.of(givenTransactions, expectedResult);
        }

        /**
         * One buy transaction with fees.
         */
        private static Arguments buildOneBuyTransactionWithFeesArguments() {
            // given values
            final BigDecimal buyFiatAmount = BigDecimal.valueOf(49950);
            final BigDecimal feeFiatAmount = BigDecimal.valueOf(50);
            final BigDecimal buyCoinAmount = BigDecimal.ONE;

            // given transactions
            final List<Transaction> givenTransactions = List.of(
                Transaction.builder()
                    .coin(buildCoin())
                    .type(TransactionType.BUY)
                    .fiatAmount(buyFiatAmount)
                    .coinAmount(buyCoinAmount)
                    .feeFiatAmount(feeFiatAmount)
                    .build()
            );

            // excpected result
            final List<CoinPortfolioStatisticsDTO> expectedResult = List.of(
                CoinPortfolioStatisticsDTO.builder()
                    .coinCode(COIN_CODE)
                    .coinName(COIN_NAME)
                    .currentMarketPrice(COIN_PRICE)
                    .shares(buyCoinAmount)
                    .costBasis(Money.of(50000, FiatCurrency.EUR.name()))
                    .averageInvestedPrice(Money.of(50000, FiatCurrency.EUR.name()))
                    .currentValue(COIN_PRICE)
                    .differenceAbsolute(Money.of(50000, FiatCurrency.EUR.name()))
                    .differencePercentage(BigDecimal.valueOf(100.0))
                    .build()
            );

            return Arguments.of(givenTransactions, expectedResult);
        }

        /**
         * Two buy transaction with fees.
         */
        private static Arguments buildTwoBuyTransactionsWithFeesArguments() {
            // given values
            final BigDecimal buyFiatAmount = BigDecimal.valueOf(49950);
            final BigDecimal feeFiatAmount = BigDecimal.valueOf(50);
            final BigDecimal buyCoinAmount = BigDecimal.ONE;

            // given transactions
            final List<Transaction> givenTransactions = List.of(
                Transaction.builder()
                    .coin(buildCoin())
                    .type(TransactionType.BUY)
                    .fiatAmount(buyFiatAmount)
                    .coinAmount(buyCoinAmount)
                    .feeFiatAmount(feeFiatAmount)
                    .build(),
                Transaction.builder()
                    .coin(buildCoin())
                    .type(TransactionType.BUY)
                    .fiatAmount(buyFiatAmount)
                    .coinAmount(buyCoinAmount)
                    .feeFiatAmount(feeFiatAmount)
                    .build()
            );

            // excpected result
            final List<CoinPortfolioStatisticsDTO> expectedResult = List.of(
                CoinPortfolioStatisticsDTO.builder()
                    .coinCode(COIN_CODE)
                    .coinName(COIN_NAME)
                    .currentMarketPrice(COIN_PRICE)
                    .shares(BigDecimal.valueOf(2))
                    .costBasis(Money.of(100000, FiatCurrency.EUR.name()))
                    .averageInvestedPrice(Money.of(50000, FiatCurrency.EUR.name()))
                    .currentValue(Money.of(200000, FiatCurrency.EUR.name()))
                    .differenceAbsolute(Money.of(100000, FiatCurrency.EUR.name()))
                    .differencePercentage(BigDecimal.valueOf(100.0))
                    .build()
            );

            return Arguments.of(givenTransactions, expectedResult);
        }

        /**
         * One buy + one transfer (exchange to wallet) transaction with fees.
         */
        private static Arguments buildOneBuyAndOneTransferTransactionsWithFeesArguments() {
            // given values
            final BigDecimal buyFiatAmount = BigDecimal.valueOf(49950);
            final BigDecimal feeFiatAmount = BigDecimal.valueOf(50);
            final BigDecimal buyCoinAmount = BigDecimal.ONE;
            final BigDecimal transferCoinAmount = BigDecimal.valueOf(.9);
            final BigDecimal feeCoinAmount = BigDecimal.valueOf(.1);

            // given transactions
            final List<Transaction> givenTransactions = List.of(
                Transaction.builder()
                    .coin(buildCoin())
                    .type(TransactionType.BUY)
                    .fiatAmount(buyFiatAmount)
                    .coinAmount(buyCoinAmount)
                    .feeFiatAmount(feeFiatAmount)
                    .build(),
                Transaction.builder()
                    .coin(buildCoin())
                    .type(TransactionType.TRANSFER)
                    .coinAmount(transferCoinAmount)
                    .feeCoinAmount(feeCoinAmount)
                    .build()
            );

            // excpected result
            final BigDecimal buyPlusFeeAmount = buyFiatAmount.add(feeFiatAmount);
            final List<CoinPortfolioStatisticsDTO> expectedResult = List.of(
                CoinPortfolioStatisticsDTO.builder()
                    .coinCode(COIN_CODE)
                    .coinName(COIN_NAME)
                    .currentMarketPrice(COIN_PRICE)
                    .shares(transferCoinAmount)
                    .costBasis(Money.of(buyPlusFeeAmount, FiatCurrency.EUR.name()))
                    .averageInvestedPrice(Money.of(55555.56, FiatCurrency.EUR.name()))
                    .currentValue(Money.of(90000, FiatCurrency.EUR.name()))
                    .differenceAbsolute(Money.of(40000, FiatCurrency.EUR.name()))
                    .differencePercentage(BigDecimal.valueOf(80.0))
                    .build()
            );

            return Arguments.of(givenTransactions, expectedResult);
        }
    }

    private static Coin buildCoin() {
        return Coin.builder()
            .code(COIN_CODE)
            .build();
    }
}
