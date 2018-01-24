package com.exchange.demo.entities;

import javax.persistence.*;

/**
 * Created by vidur on 20/01/18.
 */
@Entity
public class ExchangeRateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "source_exchange_id", referencedColumnName = "id")
    private Exchange sourceExchange;

    @OneToOne
    @JoinColumn(name = "destination_exchange_id", referencedColumnName = "id")
    private Exchange destinationExchange;


    private String sourceCurrency;

    private String destinationCurrency;

    public ExchangeRateRequest() {

    }

    public ExchangeRateRequest(Exchange sourceExchange, Exchange destinationExchange, String sourceCurrency, String destinationCurrency) {
        this.sourceExchange = sourceExchange;
        this.destinationExchange = destinationExchange;
        this.sourceCurrency = sourceCurrency;
        this.destinationCurrency = destinationCurrency;
    }

    public Long getId() {
        return id;
    }

    public Exchange getSourceExchange() {
        return sourceExchange;
    }

    public void setSourceExchange(Exchange sourceExchange) {
        this.sourceExchange = sourceExchange;
    }

    public Exchange getDestinationExchange() {
        return destinationExchange;
    }

    public void setDestinationExchange(Exchange destinationExchange) {
        this.destinationExchange = destinationExchange;
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

    @Override
    public String toString() {
        return "ExchangeRateRequest{" +
                "id=" + id +
                ", sourceExchange=" + sourceExchange +
                ", destinationExchange=" + destinationExchange +
                ", sourceCurrency='" + sourceCurrency + '\'' +
                ", destinationCurrency='" + destinationCurrency + '\'' +
                '}';
    }
}
