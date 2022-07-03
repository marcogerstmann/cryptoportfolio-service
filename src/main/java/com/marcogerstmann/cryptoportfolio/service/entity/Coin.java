package com.marcogerstmann.cryptoportfolio.service.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@EqualsAndHashCode(callSuper = false)
public class Coin extends BaseEntity {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "coin", fetch = FetchType.LAZY)
    private List<CoinStorage> coinStorages = new ArrayList<>();
}
