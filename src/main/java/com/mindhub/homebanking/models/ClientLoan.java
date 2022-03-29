package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
public class ClientLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private long id;
    private double amount;
    Integer payments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client_owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan_owner;

    public ClientLoan() {
    }

    public ClientLoan(double amount, Integer payments) {
        this.amount = amount;
        this.payments=payments;
    }

    public ClientLoan(double amount, Integer payments, Client client, Loan loan){
        this.amount = amount;
        this.payments=payments;
        this.client_owner=client;
        this.loan_owner=loan;

    }

    public void setClient(Client client_owner) {
        this.client_owner = client_owner;
    }

    public void setLoan(Loan loan_owner) {
        this.loan_owner = loan_owner;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Client getClient() {
        return client_owner;
    }

    public Loan getLoan() {
        return loan_owner;
    }

    public Integer getPayments() {
        return payments;
    }


}