package com.exchange.demo.graph;

import com.exchange.demo.factories.ExchangeFactory;
import com.exchange.demo.entities.Exchange;
import com.exchange.demo.entities.ExchangePriceUpdate;
import com.exchange.demo.entities.ExchangeRateRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by vidur on 20/01/18.
 * <p>
 * Assumptions:
 * Edge between different exchanges with same currency has value 1.0
 * All Exchanges will always have edges with same currency
 */
@Component
public class ExchangeRateGraph {

    private static final Log log = LogFactory.getLog(ExchangeRateGraph.class);

    private List<ExchangeCurrencyNode> nodes = new ArrayList<>();

    @Autowired
    ExchangeFactory exchangeFactory;

    private void addNode(ExchangeCurrencyNode node) {
        if (node != null)
            nodes.add(node);
    }

    private ExchangeCurrencyNode findIfItExistsOrCreateAndGet(Exchange exchange, String currency, boolean addNodeFlag) {
        ExchangeCurrencyNode node = findNode(exchange, currency);
        if (node == null) {
            node = new ExchangeCurrencyNode(exchange, currency);
            if (addNodeFlag)
                addNode(node);
        }
        return node;
    }

    private ExchangeCurrencyNode findNode(Exchange exchange, String currency) {
        ExchangeCurrencyNode node = new ExchangeCurrencyNode(exchange, currency);
        int index = nodes.indexOf(node);
        if (index >= 0) {
            return nodes.get(index);
        }
        return null;
    }

    public synchronized void updateGraph(ExchangePriceUpdate priceUpdate) {
        updateGraph(priceUpdate.getExchange(), priceUpdate.getSourceCurrency(), priceUpdate.getDestinationCurrency(), priceUpdate.getTimestamp(), priceUpdate.getForwardFactor());
        updateGraph(priceUpdate.getExchange(), priceUpdate.getDestinationCurrency(), priceUpdate.getSourceCurrency(), priceUpdate.getTimestamp(), priceUpdate.getBackwardFactor());
    }

    private void updateGraph(Exchange exchange, String fromCurrency, String toCurrency, Date timestamp, Double value) {
        // find or create node(s) in exchange
        ExchangeCurrencyNode fromNode = findIfItExistsOrCreateAndGet(exchange, fromCurrency, true);
        ExchangeCurrencyNode toNode = findIfItExistsOrCreateAndGet(exchange, toCurrency, true);

        // update edge if found and timestamp is greater than current
        boolean addNewEdge = true;
        for (ExchangeCurrencyEdge edge : fromNode.getEdges()) {
            if (edge.getToExchangeCurrencyNode().equals(toNode)) {
                addNewEdge = false;
                if (edge.getLastTime().before(timestamp)) {
                    edge.setLastTime(timestamp);
                    edge.setValue(value);
                }
                break;
            }
        }

        // add edge
        if (addNewEdge) {
            ExchangeCurrencyEdge edge = new ExchangeCurrencyEdge(fromNode, toNode, value, timestamp);
            fromNode.addEdge(edge);
        }
    }

    public List<ExchangeCurrencyEdge> getBestRatePath(ExchangeRateRequest request) {
        return getBestRatePath(request.getSourceExchange(), request.getSourceCurrency(), request.getDestinationExchange(), request.getDestinationCurrency());
    }

    private void initializeMemory(double[][] memory, boolean[] visited) {
        for (double[] row: memory)
            Arrays.fill(row, -1.0);
        Arrays.fill(visited, false);
    }

    private double findBestPathInGraph(double[][] memory, boolean[] visited, Stack<ExchangeCurrencyEdge> path, ExchangeCurrencyNode sourceNode, ExchangeCurrencyNode targetNode) {
        int startIndex = nodes.indexOf(sourceNode);
        int endIndex = nodes.indexOf(targetNode);
        visited[startIndex] = true;
        if(memory[startIndex][endIndex] < 0) {
            if(sourceNode == targetNode) {
                return 1.0;
            }
            double maxDistance = -1.0;
            ExchangeCurrencyEdge nextEdge = null;
            for (ExchangeCurrencyEdge edge : sourceNode.getEdges()) {
                ExchangeCurrencyNode newDestination = edge.getToExchangeCurrencyNode();
                int targetIndex = nodes.indexOf(newDestination);
                if(visited[targetIndex]) continue;
                double value = edge.getValue() * findBestPathInGraph(memory, visited, path, newDestination, targetNode);
                if(value == 0) continue;
                if(value > maxDistance) {
                    maxDistance = value;
                    nextEdge = edge;
                }
            }
            if(nextEdge != null) {
                path.push(nextEdge);
                memory[startIndex][nodes.indexOf(nextEdge.getToExchangeCurrencyNode())] = maxDistance;
                return maxDistance;
            } else return 0.0;
        } else {
            return memory[startIndex][endIndex];
        }
    }

    private List<ExchangeCurrencyEdge> getBestRatePath(Exchange sourceExchange, String sourceCurrency, Exchange destinationExchange, String destinationCurrency) {

        // TODO: Add recursion to find best paths with least value instead of connected
        ExchangeCurrencyNode sourceExchangeNode = findNode(sourceExchange, sourceCurrency);
        ExchangeCurrencyNode destinationExchangeNode = findNode(destinationExchange, destinationCurrency);

        if (sourceExchangeNode == null) {
            return null;
        }

        if (destinationExchangeNode == null) {
            return null;
        }

        // As assumption is sourceExchange, currency will have edge with destinationExchange, currecny with value 1.0
        ExchangeCurrencyNode destinationNode = findIfItExistsOrCreateAndGet(sourceExchange, destinationCurrency, false);
        double[][] memory = new double[nodes.size()][nodes.size()];
        boolean[] visited = new boolean[nodes.size()];

        initializeMemory(memory, visited);
        Stack<ExchangeCurrencyEdge> stack = new Stack<>();
        double bestValue = findBestPathInGraph(memory, visited, stack, sourceExchangeNode, destinationNode);
        List<ExchangeCurrencyEdge> path = new ArrayList<>();
        while (!stack.isEmpty()) {
            path.add(stack.pop());
        }
        if (path.size() > 0) {
            if (sourceExchange != destinationExchange)
                path.add(new ExchangeCurrencyEdge(destinationNode, destinationExchangeNode, 1.0, new Date()));
            return path;
        }
        return null;
    }

    public String printGraph() {
        String output = "";
        for (ExchangeCurrencyNode node : nodes) {
            // print actual edges
            Exchange currentExchange = node.getExchange();
            String currentCurrency = node.getCurrency();
            for (ExchangeCurrencyEdge edge : node.getEdges()) {
                output += node.toString() + " -- " + edge.getValue() + " --> " + edge.getToExchangeCurrencyNode().toString() + "\n";
            }
            List<Exchange> exchanges = exchangeFactory.getAllExchanges();
            for (Exchange exchange : exchanges) {
                if (exchange == currentExchange)
                    continue;
                ExchangeCurrencyNode virtualConnectedNode = findNode(currentExchange, currentCurrency);
                if (virtualConnectedNode != null) {
                    output += node.toString() + " -- " + "1.0" + " --> " + virtualConnectedNode.toString() + "\n";
                }
            }
        }
        return output;
    }
}
