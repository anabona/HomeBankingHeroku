package com.mindhub.homebanking.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private long id;

    private LocalDateTime  creationDate;
    private double balance;
    private String number;
    private AccountType accountType;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    Set<Transaction> transactions=new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Client client;

    public Account() {}

    public Account(long id, LocalDateTime creationDate, double balance, String number) {
        this.id = id;
        this.creationDate = creationDate;
        this.balance = balance;
        this.number = number;
    }

    public Account(LocalDateTime creationDate, double balance, String number) {
        this.creationDate = creationDate;
        this.balance = balance;
        this.number = number;
    }

    public Account(LocalDateTime creationDate,double balance, String number, Client client){
        this.creationDate = creationDate;
        this.balance = balance;
        this.number = number;
        this.client=client;
    }

    public Account(LocalDateTime creationDate,double balance, String number, Client client, AccountType accountType){
        this.creationDate = creationDate;
        this.balance = balance;
        this.number = number;
        this.client=client;
        this.accountType=accountType;
    }


    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public long getId() {
        return id;
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

    public Client getClient() { return client; }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    //Agrego una transacción a la lista de transacciones de la cuenta, si es CREDITO se suma el monto al balance, si es DEBITO resto al balance.
    public void addTransaction(Transaction transaction){
        if(transaction.getType().equals(TransactionType.DEBITO)){
            if (transaction.getAmount()<this.balance){
                this.balance=this.balance-transaction.getAmount();
                this.transactions.add(transaction);
                transaction.setAccount(this);
            }else{
                System.out.println("NO SE PUEDE REALIZAR LA TRANSACCION POR FALTA DE FONDOS");
            }
        }

        if (transaction.getType().equals(TransactionType.CREDITO)){
            this.balance = this.balance+transaction.getAmount();
            this.transactions.add(transaction);
            transaction.setAccount(this);
        }

    }
}
