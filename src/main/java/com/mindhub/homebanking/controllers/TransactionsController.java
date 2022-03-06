package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.TransactionDTO;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class TransactionsController {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/transactions")
        public List<TransactionDTO> getTransactions(){
            return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(Collectors.toList());
        }

        @GetMapping("/transactions/{id}")
        public TransactionDTO getTransactions(@PathVariable long id){
            return transactionRepository.findById(id).map(TransactionDTO::new).orElse(null);
        }

    /*@GetMapping("/transaction")
    public List<transactionDTO> getTransactions(){
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(toList());
    }*/

      /*  //Modifico el saldo
        accountRepository.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        accountRepository.save((account));
*/
}
