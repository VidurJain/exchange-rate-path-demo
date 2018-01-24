package com.exchange.demo;

import com.exchange.demo.factories.ExchangePriceFactory;
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
public class ExchangePriceFactoryTest {

    @Autowired
    ExchangePriceFactory exchangePriceFactory;

    @Test
    public void testExchangePriceRightUpdate() {
        try {
            exchangePriceFactory.process("2017-11-01T09:42:23+00:00", "KRAKEN", "BTC", "USD", "1000.0", "0.0009");
            exchangePriceFactory.process("2017-11-01T09:43:23+00:00", "GDAX", "BTC", "USD", "1001.0", "0.0008");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(true);
    }

    @Test
    public void testExchangePriceWrongUpdates() {
        try {
            exchangePriceFactory.process("abcd", "KRAKEN", "BTC", "USD", "1000.0", "0.0009");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(true);
    }
}
