package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.CardUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class ClientTests {
    @Autowired
    ClientRepository clientRepository;

    @Test
    public void findAClient() {
        List<Client> clientEmail = clientRepository.findAll();
        assertThat(clientEmail, is(Matchers.notNullValue()));

    }

    @Test
    public void clientNameIsCreated() {
        List<Client> clientId = clientRepository.findAll();
        assertThat(clientId, is(Matchers.not(empty())));
    }
}
