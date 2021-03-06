package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @RequestMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> takeLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){


        //Se busca en el repositorio el préstamo y el cliente con los datos que vienen de loanApplicationDTO desde el front
        Loan loan = loanRepository.findByName(loanApplicationDTO.getLoanName());
        Client client = clientRepository.findByEmail(authentication.getName());

        double amount = Double.parseDouble(loanApplicationDTO.getMonto());
        Integer cuotas = Integer.valueOf(loanApplicationDTO.getCuotas());

        String description = "Credito de tipo: "+loan.getName()+" otorgado a la cuenta: "+loanApplicationDTO.getCuentaDestino();

        if (amount>loan.getMaxAmount()){
            return new ResponseEntity<>("El monto solicitado es mayor que el permitido para este Prestamo", HttpStatus.FORBIDDEN);
        }

        if(Objects.isNull(loan)){
            return new ResponseEntity<>("El tipo de prestamo solicitado no existe", HttpStatus.FORBIDDEN);
        }

        if(amount<=0){
            return new ResponseEntity<>("El monto ingresado para el préstamo es inválido", HttpStatus.FORBIDDEN);
        }

        if (cuotas==0){
            return new ResponseEntity<>("La cantidad de cuotas ingresado es 0, por favor elija una cantidad de cuotas válida", HttpStatus.FORBIDDEN);
        }

        //Transacción de tipo credit a la cuenta destino del usuario verificado
        Account cuentaDestino = accountRepository.findByNumber(loanApplicationDTO.getCuentaDestino());

        if (Objects.isNull(cuentaDestino)){
            return new ResponseEntity<>("La Cuenta de destino es inválida", HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction(TransactionType.CREDITO,amount,description, LocalDateTime.now());
        cuentaDestino.addTransaction(transaction);

        transactionsRepository.save(transaction);

        //Hay que sumarle 1 al interés del préstamo para calcular el monto final que debe pagar.
        double finalInterest = 1+loan.getInterest();

        double amountFinal = amount * finalInterest;
        ClientLoan clientLoan = new ClientLoan(amountFinal,cuotas,client,loan);
        clientLoanRepository.save(clientLoan);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


}