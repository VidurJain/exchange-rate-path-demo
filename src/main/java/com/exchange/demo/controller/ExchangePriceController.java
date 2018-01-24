package com.exchange.demo.controller;

import com.exchange.demo.factories.ExchangePriceFactory;
import com.exchange.demo.controller.entities.ExchangePriceUpdateDTO;
import com.exchange.demo.graph.ExchangeCurrencyEdge;
import com.exchange.demo.graph.ExchangeRateGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vidur on 21/01/18.
 */
@RestController
@RequestMapping("/api/v1")
public class ExchangePriceController {

    @Autowired
    private ExchangePriceFactory exchangePriceFactory;

    @Autowired
    private ExchangeRateGraph exchangeRateGraph;

    @RequestMapping(value = "/exchange-best-price", method = RequestMethod.GET)
    public String bestPrice(@RequestParam(value = "source_exchange", required = true) String sourceExchange,
                            @RequestParam(value = "source_currency", required = true) String sourceCurrency,
                            @RequestParam(value = "destination_exchange", required = true) String destinationExchange,
                            @RequestParam(value = "destination_currency", required = true) String destinationCurrency) throws Exception {

        List<ExchangeCurrencyEdge> path = exchangePriceFactory.getBestPrice(sourceExchange, sourceCurrency, destinationExchange, destinationCurrency);
        if (path == null || path.size() == 0) {
            throw new Exception("Cannot Server Request. Not enough data");
        }
        String output = "";
        double value = 1.0;

        ExchangeCurrencyEdge sourceEdge = path.get(0);
        ExchangeCurrencyEdge destinationEdge = null;
        output += sourceEdge.getFromExchangeCurrencyNode().toString() + "\n";
        for (ExchangeCurrencyEdge edge : path) {
            destinationEdge = edge;
            value *= edge.getValue();
            output += destinationEdge.getToExchangeCurrencyNode().toString() + "\n";
        }

        output = "BEST_RATES_BEGIN " + sourceEdge.getFromExchangeCurrencyNode().toString() + " " + destinationEdge.getToExchangeCurrencyNode().toString() + " " + value
                + "\n" + output + "BEST_RATES_END" + "\n";
        return output;
    }

    @RequestMapping(value = "/exchange-print-graph", method = RequestMethod.GET)
    public String graphOutPut() {
        return exchangeRateGraph.printGraph();
    }

    @RequestMapping(value = "/exchange-price-update", method = RequestMethod.POST)
    public String addExchangePriceUpdate(@RequestBody ExchangePriceUpdateDTO exchangePriceUpdate) throws Exception {
        exchangePriceFactory.process(exchangePriceUpdate.getTimestamp(), exchangePriceUpdate.getExchange(),
                exchangePriceUpdate.getSourceCurrency(), exchangePriceUpdate.getDestinationCurrency(), exchangePriceUpdate.getForwardFactor(),
                exchangePriceUpdate.getBackwardFactor());
        return "Update Queued";
    }

}
