package com.mindhub.homebanking.controllers;

import antlr.Utils;
import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccounts(Authentication authentication) {
        ClientDTO clientDTO = new ClientDTO(clientRepository.findByEmail(authentication.getName()));
        Set<AccountDTO> accounts = clientDTO.getAccounts();
        return accounts;
    }

    @GetMapping("/accounts/{number}")
    public AccountDTO getAccount(@PathVariable String number) {
        AccountDTO accountDTO = new AccountDTO(accountRepository.findByNumber(number));
        return  accountDTO;
    }


    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> creator(Authentication authentication) {
        Client clientCurrent = clientRepository.findByEmail(authentication.getName());
        if (clientCurrent.getAccounts().size() > 2) {
            return new ResponseEntity<>("Ya tienes 3 cuentas", HttpStatus.FORBIDDEN);
        }

        int numeroRandom = (int) (Math.random() * (99999999 - 10000000 + 1) + 10000000);
        Account nuevaCuenta = new Account("VIN-" + numeroRandom, LocalDateTime.now(), 0.00, clientCurrent, true);
        accountRepository.save(nuevaCuenta);

        return new ResponseEntity<>("201 Creada", HttpStatus.CREATED);
    }
    @PatchMapping("/clients/current/accounts/delete/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long id) {
        Account deleteAccount = accountRepository.findById(id).orElse(null);
        deleteAccount.setStatus((false));
        accountRepository.save(deleteAccount);
        return new ResponseEntity<>("201 Cuenta Eliminada", HttpStatus.CREATED);
    }
}




