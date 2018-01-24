package com.exchange.demo.controller.entities;

import com.exchange.demo.entities.Exchange;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by vidur on 20/01/18.
 */
public class ExchangePriceUpdateDTO implements Serializable {

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("exchange")
    private String exchange;

    @JsonProperty("source_currency")
    private String sourceCurrency;

    @JsonProperty("destination_currency")
    private String destinationCurrency;

    @JsonProperty("forward_factor")
    private String forwardFactor;

    @JsonProperty("backward_factor")
    private String backwardFactor;


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getDestinationCurrency() {
        return destinationCurrency;
    }

    public void setDestinationCurrency(String destinationCurrency) {
        this.destinationCurrency = destinationCurrency;
    }

    public String getForwardFactor() {
        return forwardFactor;
    }

    public void setForwardFactor(String forwardFactor) {
        this.forwardFactor = forwardFactor;
    }

    public String getBackwardFactor() {
        return backwardFactor;
    }

    public void setBackwardFactor(String backwardFactor) {
        this.backwardFactor = backwardFactor;
    }
}

