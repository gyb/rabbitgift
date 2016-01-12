package com.irelint.ttt.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.irelint.ttt.dto.OrderDto;
import com.irelint.ttt.dto.OrderHistoryDto;
import com.irelint.ttt.dto.RatingDto;

public interface OrderApi {

	OrderDto get(Long orderId);

	OrderDto create(OrderDto order);

	Page<OrderDto> findSellerOrders(Long userId, Pageable pageable);

	Page<OrderDto> findBuyerOrders(Long userId, Pageable pageable);

	Page<RatingDto> findRatings(Long goodsId, Pageable pageable);

	void pay(Long orderId);

	OrderDto cancel(Long orderId);

	OrderDto deliver(Long orderId);

	OrderDto refund(Long orderId);

	OrderDto receive(Long orderId);

	OrderDto rate(RatingDto rating);

	OrderDto findDetail(Long orderId);

	List<OrderHistoryDto> history(Long orderId);

}