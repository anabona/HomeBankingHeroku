package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> doTransaction(
            @RequestParam String originAccountNumber,
            @RequestParam String destinyAccountNumber,
            @RequestParam String strAmount,
            @RequestParam String description) {

        double amount = Double.parseDouble(strAmount);

        Account originAccount = accountRepository.findByNumber(originAccountNumber);
        Account destinyAccount = accountRepository.findByNumber(destinyAccountNumber);

     if (originAccountNumber.equals("") || destinyAccountNumber.equals("") ||
             amount == 0 || description.equals("")) {
                return new ResponseEntity<>("There are no enough information", HttpStatus.FORBIDDEN);
     };

     if (destinyAccount == null) {
         return new ResponseEntity<>("The destiny account is invalid", HttpStatus.FORBIDDEN);
     }

     if (originAccountNumber.equals(destinyAccountNumber)) {
         return new ResponseEntity<>("Both accounts are the same", HttpStatus.FORBIDDEN);
     }

     if (originAccount.getBalance() < amount) {
         return new ResponseEntity<>("Amount under Account balance", HttpStatus.FORBIDDEN);
     }

        String destinyDescription = description + " - " +
                originAccount.getClient().getFirstName() + " " +
                originAccount.getClient().getLastName();

        String originDescription = description + " - a: " +
                destinyAccount.getClient().getFirstName() + " " +
                destinyAccount.getClient().getLastName();

        Transaction transactionCredit = new Transaction("Transferencia1", LocalDateTime.now(), TransactionType.CREDIT,
                amount, destinyDescription, destinyAccount);
        transactionRepository.save(transactionCredit);

        Transaction transactionDebit = new Transaction("Transferencia1", LocalDateTime.now(), TransactionType.DEBIT,
                amount, originDescription, originAccount);
        transactionRepository.save(transactionDebit);

        originAccount.addTransaction(transactionCredit);
        originAccount.addTransaction(transactionDebit);

        double creditAmount = destinyAccount.getBalance() - amount;
        double debitAmount = destinyAccount.getBalance() + amount;

        destinyAccount.setBalance(creditAmount);
        destinyAccount.setBalance(debitAmount);


        return new ResponseEntity<>(HttpStatus.CREATED);

                    }

    }


















