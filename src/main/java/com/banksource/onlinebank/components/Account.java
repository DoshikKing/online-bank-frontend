package com.banksource.onlinebank.components;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "account_number", nullable = false)
    private String account_number;

    @Column(name = "account_name", nullable = false)
    private String account_name;

    @Column(name = "is_credit",nullable = false)
    private Boolean is_credit;

    @Column(name = "open_date",nullable = false)
    private Date open_date;

    @Column(name = "status_time",nullable = false)
    private Date status_time;

    @Column(name = "balance",nullable = false)
    private float balance;

    @Column(name = "limit_per_day")
    private float limit_per_day;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    public Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tariff_id", nullable = false)
    public Tariff tariff;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    public Status status;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
