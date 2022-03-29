package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.extra.Utilities;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable long id){
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @RequestMapping("/account/{accountNumber}")
    public String getNombreTitularCuenta(@PathVariable String accountNumber){
        Account cuenta = accountRepository.findByNumber(accountNumber);
        return cuenta.getClient().getFirstName()+" "+cuenta.getClient().getLastName();
    }

    //Servicio que crea una cuenta nueva
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(@RequestParam String tipoCuenta,Authentication authentication){
        AccountType accountType=AccountType.valueOf(tipoCuenta);
        Utilities utilities =new Utilities();
        Client currentClient =  clientRepository.findByEmail(authentication.getName());

        if (currentClient.getAccounts().size() > 2){
            return new ResponseEntity<>("El cliente ya posee 3 cuentas", HttpStatus.FORBIDDEN);
        }


        Account newAccount= new Account( LocalDateTime.now(),0, utilities.getRandomAccountNumber(), currentClient,accountType);
        currentClient.addAccount(newAccount);
        accountRepository.save(newAccount);

        return new ResponseEntity<>("200 Cuenta creada correctamente",HttpStatus.CREATED);
    }


    @DeleteMapping("/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(Authentication authentication){
        return new ResponseEntity<>("200 Cuenta borrada con éxito",HttpStatus.CREATED);
    }

}


