package com.banksource.onlinebank.components;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pay_system")
@Getter
@Setter
public class PaySystem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @OneToOne(mappedBy = "paySystem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public BankCard bankCard;
}
