package com.marcogerstmann.cryptoportfolio.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
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
public class Coin extends BaseEntity {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "coin")
    private List<CoinStorage> coinStorages;

    @JsonIgnore
    @OneToMany(mappedBy = "coin")
    private List<Transaction> transactions;
}
