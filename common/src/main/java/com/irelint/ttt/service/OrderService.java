package com.irelint.ttt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.irelint.ttt.dto.OrderDto;
import com.irelint.ttt.dto.OrderHistoryDto;
import com.irelint.ttt.dto.RatingDto;
import com.irelint.ttt.event.GoodsCreatedEvent;
import com.irelint.ttt.event.GoodsUpdatedEvent;
import com.irelint.ttt.event.OrderConfirmedEvent;
import com.irelint.ttt.event.OrderPayedEvent;

public interface OrderService {

	OrderDto create(OrderDto order);

	void confirmed(OrderConfirmedEvent event);

	Page<OrderDto> findBuyerOrders(Long userId, Pageable pageable);

	OrderDto get(Long orderId);

	void pay(Long orderId);

	void pay(OrderPayedEvent event);

	Page<OrderDto> findSellerOrders(Long userId, Pageable pageable);

	OrderDto cancel(Long orderId);

	OrderDto deliver(Long orderId);

	OrderDto refund(Long orderId);

	OrderDto receive(Long orderId);

	OrderDto rate(RatingDto rating);

	List<OrderHistoryDto> history(Long orderId);

	Page<RatingDto> findRatings(Long goodsId, Pageable pageable);

	void createGoods(GoodsCreatedEvent event);
	
	void updateGoods(GoodsUpdatedEvent event);
}