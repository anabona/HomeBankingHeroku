package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.utils.CardUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
public class TransactionsTests {
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existTransaction() {
        List<Transaction> transactionName = transactionRepository.findAll();
        assertThat(transactionName, is(Matchers.not(empty())));
    }

    @Test
    public void existTransactionAmount() {
        List<Transaction> transactionAmount = transactionRepository.findAll();
        assertThat(transactionAmount,is(Matchers.notNullValue()));
    }
}