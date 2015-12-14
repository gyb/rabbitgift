package com.irelint.ttt.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderDao extends PagingAndSortingRepository<Order, Long> {

	Page<Order> findByBuyerId(Long buyerId, Pageable pageable);

	Page<Order> findBySellerId(Long sellerId, Pageable pageable);

}
