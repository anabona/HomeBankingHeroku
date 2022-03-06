package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String number;
    private LocalDateTime creationDateTime = LocalDateTime.now();
    private Double balance;
    public boolean status;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "client_id")
    private Client client;

    @OneToMany (mappedBy = "account", fetch = FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    public Account() {
    }

    public Account(String number, LocalDateTime creationDate, Double balance, Client client, boolean status) {
        this.number = number;
        this.creationDateTime = creationDateTime;
        this.balance = balance;
        this.client = client;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDateTime;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDateTime = creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void addTransaction (Transaction transaction){
        if (getTransactions().equals(TransactionType.CREDIT)) {
            balance = transaction.getAmount()+getBalance();
        };

        if (getTransactions().equals(TransactionType.DEBIT)) {
            balance = getBalance() - transaction.getAmount();
        };
    }

}
