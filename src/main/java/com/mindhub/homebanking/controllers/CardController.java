package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.extra.Utilities;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam String color, @RequestParam String tipo){

        CardColor colorTarjeta = CardColor.valueOf(color);
        CardType tipoTarjeta = CardType.valueOf(tipo);

        Utilities utilities = new Utilities();

        Client currentClient = clientRepository.findByEmail(authentication.getName());

        if( currentClient.getCards().stream().filter(tarjeta->
        {
            boolean b = tarjeta.getCardType().toString().equals(tipo.toString()) && tarjeta.getActivada();
            return b;
        }).count()>2){
            return new ResponseEntity<>("El cliente ya posee 3 tarjetas del mismo tipo", HttpStatus.FORBIDDEN);
        }

        Card newCard = new Card(utilities.getRandomCardNumber(), utilities.getRandomCVV(), LocalDateTime.now().plusYears(5), LocalDateTime.now(),tipoTarjeta, colorTarjeta, clientRepository.findByEmail(authentication.getName()),true);
        cardRepository.save(newCard);
        return new ResponseEntity<>("201 Tarjeta creada correctamente",HttpStatus.CREATED);
    }

    //Metodo para cambiar el estado de una tarjeta
    @PatchMapping("clients/delete/card")
    public ResponseEntity<Object> deleteCard(@RequestParam String cardNumber){
        System.out.println(cardNumber);
        Card card=cardRepository.findByNumber(cardNumber);
        card.setActivada(false);

        cardRepository.save(card);
        return new ResponseEntity<>("201 Tarjeta eliminada correctamente",HttpStatus.CREATED);
    }

}