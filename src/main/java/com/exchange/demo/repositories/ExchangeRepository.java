package com.exchange.demo.repositories;

import com.exchange.demo.entities.Exchange;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by vidur on 21/01/18.
 */
public interface ExchangeRepository extends PagingAndSortingRepository<Exchange, Long> {

    Exchange findByName(String name);
}
