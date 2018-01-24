package com.exchange.demo.repositories;

import com.exchange.demo.entities.ExchangePriceUpdate;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by vidur on 21/01/18.
 */
public interface ExchangePriceUpdateRepository extends PagingAndSortingRepository<ExchangePriceUpdate, Long> {

}
