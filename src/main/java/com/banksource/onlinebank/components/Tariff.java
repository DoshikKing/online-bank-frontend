package com.banksource.onlinebank.components;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tariff")
@Getter
@Setter
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tariff_name", nullable = false)
    private String tariffName;
    @Column(name = "tariff_percentage", nullable = false)
    private float tariffPercentage;

    @OneToMany(mappedBy = "tariff", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Account> accounts;
    @OneToMany(mappedBy = "tariff", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BankCard> bankCards;
}
