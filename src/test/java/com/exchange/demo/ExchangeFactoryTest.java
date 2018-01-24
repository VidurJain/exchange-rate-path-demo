package com.exchange.demo;

import com.exchange.demo.factories.ExchangeFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by vidur on 21/01/18.
 */
@RunWith(SpringRunner.class)
@SpringBootApplication
public class ExchangeFactoryTest {

    @Autowired
    ExchangeFactory exchangeFactory;

    @Test
    public void testExchangeFactoryInsert() {
        exchangeFactory.createAndGetExchange("KRAKEN");
        Assert.assertEquals(exchangeFactory.getAllExchanges().size(), 1);
    }

}
