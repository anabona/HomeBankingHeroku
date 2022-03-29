package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;


    @Transactional
    @PostMapping("/payment")
    public ResponseEntity<Object> makePayment(@RequestParam String number,@RequestParam String cardHolder, @RequestParam Integer cvv, @RequestParam String expiredMonth, @RequestParam String expiredYear, @RequestParam String accountNumber, @RequestParam String montoADebitar,@RequestParam String descripcionPago){

        Card card=cardRepository.findByNumber(number);

        Account account=accountRepository.findByNumber(accountNumber);

        double monto = Double.parseDouble(montoADebitar);

        //Me fijo si está vencida
        LocalDateTime fechaActual =LocalDateTime.now();
        String mesActual = String.valueOf(fechaActual.getMonth());
        String añoActual = String.valueOf(fechaActual.getYear());

       if (!card.getCardHolder().toUpperCase(Locale.ROOT).equals(cardHolder.toUpperCase())){
            return new ResponseEntity<>("El nombre asociado a la tarjeta es inválido", HttpStatus.FORBIDDEN);
        }

        if (!card.getCvv().equals(cvv)){
            return new ResponseEntity<>("El código de seguridad es incorrecto", HttpStatus.FORBIDDEN);
        }

        if (account.getBalance()<monto){
            return new ResponseEntity<>("El monto en la cuenta es insuficiente para realizar el pago", HttpStatus.FORBIDDEN);
        }

        if (card.getExpireDateThru().isBefore(LocalDateTime.now())){
            return new ResponseEntity<>("La tarjeta se encuentra vencida", HttpStatus.FORBIDDEN);
        }

        String descripcionDebito = montoADebitar+ " / " + descripcionPago +" / Abonado con Tarjeta: "+card.getNumber();
        Transaction transaction = new Transaction(TransactionType.DEBITO,monto,descripcionDebito, LocalDateTime.now());
        account.addTransaction(transaction);
        transactionsRepository.save(transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}