package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans () {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> askLoan(
            @RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        Client clientCurrent = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findByNumber(loanApplicationDTO.getAccount());
        Loan loan = loanRepository.findByName(loanApplicationDTO.getName());


            if (loanApplicationDTO.getName().isEmpty() || loanApplicationDTO.getAccount().isEmpty() || loanApplicationDTO.getAmount() <= 0 ||
                    loanApplicationDTO.getPayment() <= 0) {
                return new ResponseEntity<>("Hay datos vacíos", HttpStatus.FORBIDDEN);
            }

            if (loan == null) {
                return new ResponseEntity<>("El préstamo seleccionado es inexistente", HttpStatus.FORBIDDEN);
            }

            if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) {
                return new ResponseEntity<>("El monto solicitado supera el tope máximo", HttpStatus.FORBIDDEN);
            }

           if (!loan.getPayments().contains(loanApplicationDTO.getPayment())) {
             return new ResponseEntity<>("Error en la elección de cuotas", HttpStatus.FORBIDDEN);
            }

            if (!clientCurrent.getAccounts().contains(account)) {
                return new ResponseEntity<>("La cuenta destino es inexistente", HttpStatus.FORBIDDEN);
            }

            if (clientCurrent==null) {
                return new ResponseEntity<>("Por favor, inicie sesión nuevamente", HttpStatus.FORBIDDEN);
            }

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getPayment(), loanApplicationDTO.getAmount(), clientCurrent, loan);
        clientLoanRepository.save(clientLoan);

        Transaction transactionCredit = new Transaction(loan.getName(), LocalDateTime.now(), TransactionType.CREDIT, loanApplicationDTO.getAmount(), "Su préstamo de tipo " + loan.getName() + "fue asignado.", account);
        transactionRepository.save(transactionCredit);

        //modifico el saldo
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        accountRepository.save((account));

        return new ResponseEntity<>("Préstamo creado con éxito!",HttpStatus.CREATED);
    }
/*
    @PostMapping("/loans/admin")
    public ResponseEntity<Object> askLoanAdmin(
             Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {

        if (loanApplicationDTO.getName() == "" || loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayment() == 0 ) {
            return new ResponseEntity<>("Hay datos vacíos", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() > 200000) {
            return new ResponseEntity<>("El monto no puede superar los $200.000", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getPayment() != 6 || loanApplicationDTO.getPayment() !=12) {
            return new ResponseEntity<>("Solo se permiten 6 o 12 cuotas", HttpStatus.FORBIDDEN);
        }

        Loan loanAdmin = new Loan(loanApplicationDTO.getName(), loanApplicationDTO.getAmount(), loanApplicationDTO.getPayment());
        LoanRepository.save(loanAdmin);

    }*/
}

