package com.banksource.onlinebank.components;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;
    @Column(name = "amount", nullable = false)
    private Long summ;
    @Column(name = "is_debit")
    private Boolean isDebit;
    @Column(name = "transaction_time", nullable = false)
    private Date transactionTime;
    @Column(name = "transaction_group", nullable = false)
    private String transactionGroup;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    public Account account;
}
