package com.exchange.demo.repositories;

import com.exchange.demo.entities.ExchangePriceUpdate;
import com.exchange.demo.entities.ExchangeRateRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by vidur on 21/01/18.
 */
public interface ExchangeRateRequestRepository extends PagingAndSortingRepository<ExchangeRateRequest, Long> {

}
