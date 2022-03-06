package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.utils.CardUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@SpringBootTest
public class CardUtilsTests {
    @Test
    public void cardNumberIsCreated(){
        int cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(Matchers.not(empty())));

    }

    @Test
    public void cvvNumberIsCreated(){
        int cvvNumber = CardUtils.getCVV();
        assertThat(cvvNumber,is(Matchers.not(empty())));

    }


}
