package com.marcogerstmann.cryptoportfolio.service.dto;

import com.marcogerstmann.cryptoportfolio.service.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TransactionDto {

    private LocalDateTime date;
    private BigDecimal fiatAmount;
    private BigDecimal coinAmount;
    private TransactionType type;
    private String fromCoinStorageName;
    private String toCoinStorageName;
}
