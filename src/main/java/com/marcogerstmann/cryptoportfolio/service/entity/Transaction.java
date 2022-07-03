package com.marcogerstmann.cryptoportfolio.service.entity;

import com.marcogerstmann.cryptoportfolio.service.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@EqualsAndHashCode(callSuper = false)
public class Transaction extends BaseEntity {

    @NotNull
    @PastOrPresent
    private LocalDateTime date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    private CoinStorage fromCoinStorage;

    @ManyToOne
    private CoinStorage toCoinStorage;

    @NotNull
    @ManyToOne
    private Coin coin;

    @NotNull
    @PositiveOrZero
    private BigDecimal fiatAmount;

    @NotNull
    @PositiveOrZero
    private BigDecimal coinAmount;

    @NotNull
    @PositiveOrZero
    private BigDecimal coinFiatValue;

    @PositiveOrZero
    private BigDecimal feeFiatAmount;

    @PositiveOrZero
    private BigDecimal feeCoinAmount;
}
