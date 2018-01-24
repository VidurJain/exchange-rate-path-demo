package com.exchange.demo.graph;

import com.exchange.demo.entities.Exchange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vidur on 21/01/18.
 */
public class ExchangeCurrencyNode {

    private Exchange exchange;
    private String currency;
    private List<ExchangeCurrencyEdge> edges = new ArrayList<>();

    public ExchangeCurrencyNode(Exchange exchange, String currency) {
        this.exchange = exchange;
        this.currency = currency;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public String getCurrency() {
        return currency;
    }

    public void addEdge(ExchangeCurrencyEdge edge) {
        edges.add(edge);
    }

    public List<ExchangeCurrencyEdge> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExchangeCurrencyNode) {
            if (this.exchange.getName().equals(((ExchangeCurrencyNode) obj).getExchange().getName()) && this.currency.equals(((ExchangeCurrencyNode) obj).getCurrency())) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getExchange().getName().hashCode();
        result = 31 * result + this.getCurrency().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "(" + this.getExchange().getName() + ", " + this.getCurrency() + ")";
    }
}
