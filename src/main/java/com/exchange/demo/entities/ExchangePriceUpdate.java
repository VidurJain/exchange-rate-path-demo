package com.exchange.demo.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vidur on 20/01/18.
 */
@Entity
public class ExchangePriceUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date timestamp;

    @OneToOne
    @JoinColumn(name = "exchange_id", referencedColumnName = "id")
    private Exchange exchange;

    private String sourceCurrency;

    private String destinationCurrency;

    private double forwardFactor;

    private double backwardFactor;

    public ExchangePriceUpdate() {

    }

    public ExchangePriceUpdate(Date timestamp, Exchange exchange, String sourceCurrency, String destinationCurrency, double forwardFactor, double backwardFactor) {
        this.timestamp = timestamp;
        this.exchange = exchange;
        this.sourceCurrency = sourceCurrency;
        this.destinationCurrency = destinationCurrency;
        this.forwardFactor = forwardFactor;
        this.backwardFactor = backwardFactor;
    }


    public Long getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
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

    public double getForwardFactor() {
        return forwardFactor;
    }

    public void setForwardFactor(double forwardFactor) {
        this.forwardFactor = forwardFactor;
    }

    public double getBackwardFactor() {
        return backwardFactor;
    }

    public void setBackwardFactor(double backwardFactor) {
        this.backwardFactor = backwardFactor;
    }

    @Override
    public String toString() {
        return "ExchangePriceUpdate{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", exchange=" + exchange +
                ", sourceCurrency='" + sourceCurrency + '\'' +
                ", destinationCurrency='" + destinationCurrency + '\'' +
                ", forwardFactor=" + forwardFactor +
                ", backwardFactor=" + backwardFactor +
                '}';
    }
}

