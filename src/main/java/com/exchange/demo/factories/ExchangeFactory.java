package com.exchange.demo.factories;

import com.exchange.demo.entities.Exchange;
import com.exchange.demo.repositories.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vidur on 20/01/18.
 */
@Component
public class ExchangeFactory {

    private Map<String, Exchange> exchangeMap = new HashMap<>();

    @Autowired
    ExchangeRepository exchangeRepository;

    public Exchange createAndGetExchange(String exchangeName) {
        if (!exchangeMap.containsKey(exchangeName))
            registerExchange(exchangeName);
        return getExchange(exchangeName);
    }

    public Exchange getExchange(String exchangeName) {
        return exchangeMap.get(exchangeName);
    }

    private void registerExchange(String exchangeName) {
        Exchange exchange = new Exchange(exchangeName);
        exchangeRepository.save(exchange);
        exchangeMap.put(exchangeName, exchange);
    }

    public List<Exchange> getAllExchanges() {
        return new ArrayList<>(exchangeMap.values());
    }

}
