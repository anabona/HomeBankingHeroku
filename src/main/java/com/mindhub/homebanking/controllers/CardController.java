package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping ("/api")
public class CardController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;

   @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> register(Authentication authentication,
            @RequestParam String color, @RequestParam String type) {
        Client clientCurrent = clientRepository.findByEmail(authentication.getName());

        if((clientCurrent.getCards().size()<3)){
        }
       int numeroRandom = CardUtils.getCardNumber();
       int numeroRandomCvv = CardUtils.getCVV();

       Card cardNew = cardRepository.save(
                new Card(clientCurrent.getFirstName() +" " + clientCurrent.getLastName(), numeroRandom, numeroRandomCvv,
                        LocalDate.now(), LocalDate.now().plusYears((long) 5), CardColor.valueOf(color), CardType.valueOf(type),clientCurrent, true));
                return new ResponseEntity<>("Successfully card creation!",HttpStatus.CREATED);
    }

   @PatchMapping("/clients/current/cards/delete/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id, @RequestParam Boolean status) {
       Card deleteCard = cardRepository.findById(id).orElse(null);
       deleteCard.setStatus(status);
       cardRepository.save(deleteCard);
       return new ResponseEntity<>("200 Tarjeta Eliminada", HttpStatus.OK);
    }

   /* @PatchMapping("/clients/delete/card")
    public ResponseEntity<Object> deleteCard(@RequestParam String cardNumber) {
        System.out.println(cardNumber);
        Card card = cardRepository.findByNumber(cardNumber);
        card.status(false);

        cardRepository.save(ca)
    }*/

}
