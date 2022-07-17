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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @PositiveOrZero
    private BigDecimal fiatAmount;

    @PositiveOrZero
    private BigDecimal coinAmount;

    @PositiveOrZero
    private BigDecimal feeFiatAmount;

    @PositiveOrZero
    private BigDecimal feeCoinAmount;
}
