package com.exchange.demo;

import com.exchange.demo.entities.Exchange;
import com.exchange.demo.entities.ExchangePriceUpdate;
import com.exchange.demo.entities.ExchangeRateRequest;
import com.exchange.demo.graph.ExchangeCurrencyEdge;
import com.exchange.demo.graph.ExchangeRateGraph;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by vidur on 21/01/18.
 */
@RunWith(SpringRunner.class)
@SpringBootApplication
public class ExchangeRateGraphTest {

    @Autowired
    ExchangeRateGraph exchangeRateGraph;

    Exchange kraken = new Exchange("KRAKEN");
    Exchange bitly = new Exchange("BITLY");
    String inrCurrency = "INR";
    String usdCurrency = "USD";
    String poundCurrency = "POUND";

    @Test
    public void testGraphBestPrice() {
        double amountFromUsdToINR = 100.0;
        Date firstDate = new Date();
        Date secondDate = new Date(firstDate.getTime() + 100);

        exchangeRateGraph.updateGraph(new ExchangePriceUpdate(firstDate, kraken, usdCurrency, inrCurrency, amountFromUsdToINR + 1, 1/(amountFromUsdToINR + 1)));
        exchangeRateGraph.updateGraph(new ExchangePriceUpdate(secondDate, kraken, usdCurrency, inrCurrency, amountFromUsdToINR, 1/amountFromUsdToINR));
        exchangeRateGraph.updateGraph(new ExchangePriceUpdate(secondDate, bitly, usdCurrency, inrCurrency, amountFromUsdToINR * 1.3, 1/amountFromUsdToINR * 1.3));

        List<ExchangeCurrencyEdge> edges = exchangeRateGraph.getBestRatePath(new ExchangeRateRequest(kraken, bitly, usdCurrency, inrCurrency));
        double value = 1.0;
        for(ExchangeCurrencyEdge edge: edges) {
            value *= edge.getValue();
        }
        Assert.assertEquals(amountFromUsdToINR, value, 0.0001);
    }
}
