package com.exchange.demo.factories;

import com.exchange.demo.entities.Exchange;
import com.exchange.demo.entities.ExchangePriceUpdate;
import com.exchange.demo.entities.ExchangeRateRequest;
import com.exchange.demo.graph.ExchangeCurrencyEdge;
import com.exchange.demo.graph.ExchangeRateGraph;
import com.exchange.demo.repositories.ExchangePriceUpdateRepository;
import com.exchange.demo.repositories.ExchangeRateRequestRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by vidur on 20/01/18.
 */
@Component
public class ExchangePriceFactory {

    private static final Log log = LogFactory.getLog(ExchangePriceFactory.class);

    @Autowired
    private ExchangeRateGraph exchangeRateGraph;

    @Autowired
    private ExchangeFactory exchangeFactory;

    @Autowired
    private ExchangePriceUpdateRepository exchangePriceUpdateRepository;

    @Autowired
    private ExchangeRateRequestRepository exchangeRateRequestRepository;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");


    public void process(String timestamp, String exchangeName, String sourceCurrency, String destinationCurrency, String forwardFactor, String backwardFactor) throws Exception {
        Exchange exchange = exchangeFactory.createAndGetExchange(exchangeName);
        try {
            ExchangePriceUpdate exchangePriceUpdate = new ExchangePriceUpdate(dateFormat.parse(timestamp), exchange, sourceCurrency, destinationCurrency, Double.parseDouble(forwardFactor), Double.parseDouble(backwardFactor));
            exchangePriceUpdateRepository.save(exchangePriceUpdate);
            exchangeRateGraph.updateGraph(exchangePriceUpdate);
        } catch (Exception e) {
            log.error("Error parsing data", e);
            throw e;
        }
    }

    public List<ExchangeCurrencyEdge> getBestPrice(String sourceExchangeName, String sourceCurrency, String destinationExchangeName, String destinationCurrency) {
        Exchange sourceExchange = exchangeFactory.getExchange(sourceExchangeName);
        Exchange destinationExchange = exchangeFactory.getExchange(destinationExchangeName);

        if (sourceExchange == null || destinationExchange == null) {
            log.error(" Either source or destination exchange not registered");
            return null; // reason exchange not registered
        }
        ExchangeRateRequest request = new ExchangeRateRequest(sourceExchange, destinationExchange, sourceCurrency, destinationCurrency);
        exchangeRateRequestRepository.save(request);

        return exchangeRateGraph.getBestRatePath(request);
    }
}
