package com.marcogerstmann.cryptoportfolio.service.mapper;

import com.marcogerstmann.cryptoportfolio.service.dto.TransactionDto;
import com.marcogerstmann.cryptoportfolio.service.entity.Transaction;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "fromCoinStorageName", source = "fromCoinStorage.name")
    @Mapping(target = "toCoinStorageName", source = "toCoinStorage.name")
    TransactionDto toDto(final Transaction transaction);

    List<TransactionDto> toDto(final List<Transaction> transactions);
}
