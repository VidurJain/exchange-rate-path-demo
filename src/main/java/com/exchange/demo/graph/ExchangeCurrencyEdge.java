package com.exchange.demo.graph;

import java.util.Date;

/**
 * Created by vidur on 21/01/18.
 */
public class ExchangeCurrencyEdge {
    private ExchangeCurrencyNode fromExchangeCurrencyNode;
    private ExchangeCurrencyNode toExchangeCurrencyNode;
    double value;
    private Date lastTime;

    public ExchangeCurrencyEdge(ExchangeCurrencyNode fromExchangeCurrencyNode, ExchangeCurrencyNode toExchangeCurrencyNode, double value, Date lastTime) {
        this.fromExchangeCurrencyNode = fromExchangeCurrencyNode;
        this.toExchangeCurrencyNode = toExchangeCurrencyNode;
        this.value = value;
        this.lastTime = lastTime;
    }

    public ExchangeCurrencyNode getFromExchangeCurrencyNode() {
        return fromExchangeCurrencyNode;
    }

    public void setFromExchangeCurrencyNode(ExchangeCurrencyNode fromExchangeCurrencyNode) {
        this.fromExchangeCurrencyNode = fromExchangeCurrencyNode;
    }

    public ExchangeCurrencyNode getToExchangeCurrencyNode() {
        return toExchangeCurrencyNode;
    }

    public void setToExchangeCurrencyNode(ExchangeCurrencyNode toExchangeCurrencyNode) {
        this.toExchangeCurrencyNode = toExchangeCurrencyNode;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}
