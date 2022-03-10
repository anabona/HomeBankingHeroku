package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;

    private LocalDateTime creationDate;
    private double balance;
    private String number;
    private Set<TransactionDTO> transactions = new HashSet<>();
    private AccountType accountType;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.number = account.getNumber();
        this.transactions=account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
        //this.accounts= client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
        this.accountType=account.getAccountType();
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public long getId() {
        return id;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public String getNumber() {
        return number;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}