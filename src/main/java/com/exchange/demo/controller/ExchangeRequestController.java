package com.exchange.demo.controller;

import com.exchange.demo.entities.Exchange;
import com.exchange.demo.entities.ExchangePriceUpdate;
import com.exchange.demo.entities.ExchangeRateRequest;
import com.exchange.demo.repositories.ExchangePriceUpdateRepository;
import com.exchange.demo.repositories.ExchangeRateRequestRepository;
import com.exchange.demo.repositories.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vidur on 21/01/18.
 */
@RestController
@RequestMapping("/api/v1")
public class ExchangeRequestController {

    @Autowired
    private ExchangeRateRequestRepository exchangeRateRequestRepository;

    @Autowired
    private ExchangePriceUpdateRepository exchangePriceUpdateRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @RequestMapping(value = "/exchange-price-updates", method = RequestMethod.GET)
    public List<ExchangePriceUpdate> getExchangePriceUpdates() {
        return (List<ExchangePriceUpdate>) exchangePriceUpdateRepository.findAll();
    }

    @RequestMapping(value = "/exchange-rate-requests", method = RequestMethod.GET)
    public List<ExchangeRateRequest> getExchangeRateRequests() {
        return (List<ExchangeRateRequest>) exchangeRateRequestRepository.findAll();
    }

    @RequestMapping(value = "/exchanges", method = RequestMethod.GET)
    public List<Exchange> getExchanges() {
        return (List<Exchange>) exchangeRepository.findAll();
    }


}
