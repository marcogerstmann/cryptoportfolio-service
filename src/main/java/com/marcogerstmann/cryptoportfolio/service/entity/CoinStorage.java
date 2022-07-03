package com.marcogerstmann.cryptoportfolio.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcogerstmann.cryptoportfolio.service.enums.CoinStorageType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@EqualsAndHashCode(callSuper = false)
public class CoinStorage extends BaseEntity {

    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CoinStorageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Coin coin;
}
