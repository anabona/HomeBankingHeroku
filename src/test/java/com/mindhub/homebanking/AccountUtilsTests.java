package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class AccountUtilsTests {
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void existAccount(){
        List<Account> numberAccount = accountRepository.findAll();
        assertThat(numberAccount,is(Matchers.not(empty())));

    }
    @Test
    public void existAccountBalance(){
        List<Account> accountBalance = accountRepository.findAll();
        assertThat(accountBalance,is(notNullValue()));

    }

}
